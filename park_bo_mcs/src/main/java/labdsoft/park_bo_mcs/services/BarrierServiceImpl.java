package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dtos.park.SendToPaymentDTO;
import labdsoft.park_bo_mcs.dtos.payment.PaymentsDTO;
import labdsoft.park_bo_mcs.models.park.*;
import labdsoft.park_bo_mcs.models.payment.PaymentHistory;
import labdsoft.park_bo_mcs.models.reporting.ParkReport;
import labdsoft.park_bo_mcs.models.reporting.ParkTimeReport;
import labdsoft.park_bo_mcs.models.user.Customer;
import labdsoft.park_bo_mcs.models.user.Status;
import labdsoft.park_bo_mcs.models.user.Vehicle;
import labdsoft.park_bo_mcs.repositories.park.*;
import labdsoft.park_bo_mcs.repositories.payment.PaymentHistoryRepository;
import labdsoft.park_bo_mcs.repositories.reporting.ParkReportRepository;
import labdsoft.park_bo_mcs.repositories.reporting.ParkTimeReportRepository;
import labdsoft.park_bo_mcs.repositories.user.CustomerRepository;
import labdsoft.park_bo_mcs.repositories.user.VehicleRepository;
import labdsoft.park_bo_mcs.rest.PaymentCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class BarrierServiceImpl implements BarrierService {

    @Autowired
    private BarrierRepository barrierRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ParkingHistoryRepository parkingHistoryRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private DisplayRepository displayRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private PaymentCommunication paymentCommunication;

    @Autowired
    private ParkReportRepository parkReportRepository;

    @Autowired
    private ParkTimeReportRepository parkTimeReportRepository;

    @Override
    public BarrierDisplayDTO entranceOpticalReader(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        BarrierDisplayDTO barrierDisplayDTO = barrierLicenseReaderDTO.toBarrierDisplayDTO(barrierLicenseReaderDTO);

        if (checksForLicensePlate(barrierLicenseReaderDTO)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "Please use the Park20 app to register and use the parking. Or use the QR code to download the app.");
        }

        if (initialChecksFailed(barrierLicenseReaderDTO)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "Please contact the park manager.");
        }

        Park park = parkRepository.findByParkNumber(barrierLicenseReaderDTO.getParkNumber());
        Vehicle vehicle = vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber());
        Customer customer = customerRepository.findByCustomerID(vehicle.getCustomerID());

        if (!customerEligibleForParking(customer)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "You have reached the maximum number of unpaid parking sessions. Please pay your parking sessions or contact the park manager.");
        }

        if (barrierStateCheck(barrierLicenseReaderDTO)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "The barrier is not active. Please contact the park manager.");
        }

        if (!parkingAvailabilityCheck(park, vehicle)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "The park is full. Please contact the park manager.");
        }

        processParking(park, vehicle, barrierLicenseReaderDTO);
        updateDisplayMessages(customer, barrierLicenseReaderDTO, false, 0.0, null);

        barrierDisplayDTO.setSuccess(true);
        barrierDisplayDTO.setMessage("Welcome to the park " + customer.getName() + "!");

        return barrierDisplayDTO;
    }

    private boolean customerEligibleForParking(Customer customer) {
        if (customer == null || customer.getStatus() == Status.BLOCKED) {
            return false;
        }
        List<PaymentHistory> paymentHistoryList = paymentHistoryRepository.findAllByCustomerIDAndPaid(customer.getCustomerID(), false);
        return paymentHistoryList.size() < 4;
    }

    private boolean barrierStateCheck(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        return barrierRepository.getBarrierByBarrierID(barrierLicenseReaderDTO.getBarrierID()).getState() != State.ACTIVE;
    }

    private boolean parkingAvailabilityCheck(Park park, Vehicle vehicle) {
        List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), true, true);
        List<Spot> listSpotsOperacional = spotRepository.getSpotsByParkIDAndOperational(park.getParkID(), true);

        List<Spot> listSpotsOccupiedByTypeAndVehicleType = checkForSpotTypeAndVehicleType(listSpotsOccupied, vehicle);
        List<Spot> listSpotsOperacionalByTypeAndVehicleType = checkForSpotTypeAndVehicleType(listSpotsOperacional, vehicle);

        return listSpotsOperacionalByTypeAndVehicleType.size() > listSpotsOccupiedByTypeAndVehicleType.size();
    }

    private void processParking(Park park, Vehicle vehicle, BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        List<Spot> listSpotsOpen = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), false, true);

        List<Spot> listSpotsOpenByTypeAndVehicleType = checkForSpotTypeAndVehicleType(listSpotsOpen, vehicle);

        Random rand = new Random();
        Spot spot = listSpotsOpenByTypeAndVehicleType.get(rand.nextInt(listSpotsOpenByTypeAndVehicleType.size()));
        spot.setOccupied(true);
        spotRepository.save(spot);

        ParkingHistory parkingHistory = ParkingHistory.builder().customerID(vehicle.getCustomerID())
                .startTime(barrierLicenseReaderDTO.getDate()).endTime(barrierLicenseReaderDTO.getDate())
                .parkId(park.getParkID()).price(0.0).hoursBetweenEntranceExit(0).minutesBetweenEntranceExit(0).build();
        parkingHistoryRepository.save(parkingHistory);

        processParkReport(parkingHistory, vehicle);
    }

    private void processParkReport(ParkingHistory parkingHistory, Vehicle vehicle) {

        ParkReport existentParkReport = parkReportRepository.findByDayAndMonthAndYearAndParkId(parkingHistory.getStartTime().get(Calendar.YEAR),
                parkingHistory.getStartTime().get(Calendar.MONTH),
                parkingHistory.getStartTime().get(Calendar.DAY_OF_MONTH),
                parkingHistory.getParkId());

        if (existentParkReport == null) {
            ParkReport parkReport = ParkReport.builder().parkId(parkingHistory.getParkId())
                    .year(parkingHistory.getStartTime().get(Calendar.YEAR))
                    .month(parkingHistory.getStartTime().get(Calendar.MONTH))
                    .day(parkingHistory.getStartTime().get(Calendar.DAY_OF_MONTH))
                    .totalVehicles(1.0)
                    .build();

            switch (vehicle.getVehicleType()) {
                case AUTOMOBILE:
                    parkReport.setTotalCars(1.0);
                    break;
                case MOTORCYCLE:
                    parkReport.setTotalMotorcycles(1.0);
                    break;
            }

            switch (vehicle.getVehicleEnergySource()) {
                case FUEL:
                    parkReport.setTotalFuel(1.0);
                    break;
                case ELECTRIC:
                    parkReport.setTotalElectrics(1.0);
                    break;
                case GPL:
                    parkReport.setTotalGPL(1.0);
                    break;
            }

            parkReportRepository.save(parkReport);
        } else {
            existentParkReport.setTotalVehicles(existentParkReport.getTotalVehicles() + 1.0);

            switch (vehicle.getVehicleType()) {
                case AUTOMOBILE:
                    existentParkReport.setTotalCars(existentParkReport.getTotalCars() + 1.0);
                    break;
                case MOTORCYCLE:
                    existentParkReport.setTotalMotorcycles(existentParkReport.getTotalMotorcycles() + 1.0);
                    break;
            }

            switch (vehicle.getVehicleEnergySource()) {
                case FUEL:
                    existentParkReport.setTotalFuel(existentParkReport.getTotalFuel() + 1.0);
                    break;
                case ELECTRIC:
                    existentParkReport.setTotalElectrics(existentParkReport.getTotalElectrics() + 1.0);
                    break;
                case GPL:
                    existentParkReport.setTotalGPL(existentParkReport.getTotalGPL() + 1.0);
                    break;
            }

            parkReportRepository.save(existentParkReport);
        }
    }

    private List<Spot> checkForSpotTypeAndVehicleType(List<Spot> listSpots, Vehicle vehicle) {
        List<Spot> listSpotsByTypeAndVehicleType = new ArrayList<>();

        for (Spot spot : listSpots) {
            if (spot.getSpotType().toString().equals(vehicle.getVehicleEnergySource().toString())
                    && spot.getSpotVehicleType().toString().equals(vehicle.getVehicleType().toString())) {
                listSpotsByTypeAndVehicleType.add(spot);
            }
        }

        return listSpotsByTypeAndVehicleType;
    }

    @Override
    public BarrierDisplayDTO exitOpticalReader(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        BarrierDisplayDTO barrierDisplayDTO = barrierLicenseReaderDTO.toBarrierDisplayDTO(barrierLicenseReaderDTO);

        if (checksForLicensePlate(barrierLicenseReaderDTO)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "Your vehicle is not registered in the system. Please contact the park manager.");
        }

        if (initialChecksFailed(barrierLicenseReaderDTO)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "Please contact the park manager.");
        }

        if (barrierStateCheck(barrierLicenseReaderDTO)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "The barrier is not active. Please contact the park manager.");
        }

        if (!processExit(barrierLicenseReaderDTO)) {
            return createFailureMessage(barrierDisplayDTO, barrierLicenseReaderDTO, "There are no occupied spots. Please contact the park manager.");
        }

        Customer customer = customerRepository.findByCustomerID(vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()).getCustomerID());

        Double money = getMoneyFromPayment(barrierLicenseReaderDTO);

        updateDisplayMessages(customer, barrierLicenseReaderDTO, true, money, null);

        barrierDisplayDTO.setSuccess(true);
        barrierDisplayDTO.setMessage("Have a nice day " + customer.getName() + "! Your total will be " + money + "€!");

        return barrierDisplayDTO;
    }

    private Double getMoneyFromPayment(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        ParkingHistory parkingHistory = parkingHistoryRepository.findByCustomerIDLatest(vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()).getCustomerID());

        parkingHistory.setEndTime(barrierLicenseReaderDTO.getDate());

        // Convert to milliseconds
        long millis1 = parkingHistory.getStartTime().getTimeInMillis();
        long millis2 = parkingHistory.getEndTime().getTimeInMillis();

        // Calculate difference in milliseconds
        long diffInMillis = Math.abs(millis2 - millis1);

        // Convert to hours and minutes
        int hours = (int) TimeUnit.MILLISECONDS.toHours(diffInMillis);
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60); // Get remaining minutes

        parkingHistory.setHoursBetweenEntranceExit(hours);
        parkingHistory.setMinutesBetweenEntranceExit(minutes);

        SendToPaymentDTO sendToPaymentDTO = SendToPaymentDTO.builder().parkID(barrierLicenseReaderDTO.getParkID()).enterPark(parkingHistory.getStartTime())
                .leftPark(parkingHistory.getEndTime()).licensePlateNumber(barrierLicenseReaderDTO.getPlateNumber()).build();

        PaymentsDTO paymentsDTO = paymentCommunication.postForPayment(sendToPaymentDTO);


        if (paymentsDTO != null) {
            parkingHistory.setPrice(paymentsDTO.getFinalPrice());

            parkingHistoryRepository.save(parkingHistory);

            processParkTimeReport(parkingHistory);

            return paymentsDTO.getFinalPrice();
        } else {
            return 0.0;
        }
    }

    private void processParkTimeReport(ParkingHistory parkingHistory) {
        ParkReport existentParkReport = parkReportRepository.findByDayAndMonthAndYearAndParkId(parkingHistory.getStartTime().get(Calendar.YEAR),
                parkingHistory.getStartTime().get(Calendar.MONTH),
                parkingHistory.getStartTime().get(Calendar.DAY_OF_MONTH),
                parkingHistory.getParkId());

        if (existentParkReport != null) {
            ParkTimeReport parkTimeReport = ParkTimeReport.builder()
                    .parkReportId(existentParkReport.getParkReportId())
                    .timePeriod((double) parkingHistory.getHoursBetweenEntranceExit() * 60.0 + (double) parkingHistory.getMinutesBetweenEntranceExit())
                    .build();

            parkTimeReportRepository.save(parkTimeReport);
        }

    }

    private boolean processExit(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        Park park = parkRepository.findByParkNumber(barrierLicenseReaderDTO.getParkNumber());
        List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), true, true);

        List<Spot> listSpotsOccupiedByTypeAndVehicleType = checkForSpotTypeAndVehicleType(listSpotsOccupied, vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()));

        if (listSpotsOccupiedByTypeAndVehicleType.isEmpty()) {
            return false;
        }

        clearSpot(listSpotsOccupiedByTypeAndVehicleType);

        return true;
    }

    private void clearSpot(List<Spot> listSpotsOccupied) {
        Random rand = new Random();
        Spot spot = listSpotsOccupied.get(rand.nextInt(listSpotsOccupied.size()));
        spot.setOccupied(false);
        spotRepository.save(spot);
    }

    private boolean checksForLicensePlate(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        return vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()) == null;
    }

    private boolean initialChecksFailed(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        return barrierRepository.getBarrierByBarrierID(barrierLicenseReaderDTO.getBarrierID()) == null || parkRepository.findByParkNumber(barrierLicenseReaderDTO.getParkNumber()) == null;
    }

    private BarrierDisplayDTO createFailureMessage(BarrierDisplayDTO barrierDisplayDTO, BarrierLicenseReaderDTO barrierLicenseReaderDTO, String message) {
        barrierDisplayDTO.setSuccess(false);
        barrierDisplayDTO.setMessage(message);

        updateDisplayMessages(null, barrierLicenseReaderDTO, false, 0.0, message);

        return barrierDisplayDTO;
    }

    private void updateDisplayMessages(Customer customer, BarrierLicenseReaderDTO barrierLicenseReaderDTO, Boolean onExit, Double money, String message) {
        List<Display> displayList = displayRepository.findAllByBarrierNumber(barrierRepository.getBarrierByBarrierID(barrierLicenseReaderDTO.getBarrierID()).getBarrierNumber());
        for (Display display : displayList) {
            if (display.getState() == State.ACTIVE) {
                if (message != null) display.setMessage(message);
                else if (onExit)
                    display.setMessage("Have a nice day " + customer.getName() + "! Your total will be " + money + "€!");
                else display.setMessage("Welcome to the park " + customer.getName() + "!");
            }
        }
        displayRepository.saveAll(displayList);
    }
}
