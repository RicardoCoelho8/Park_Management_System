package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dtos.park.SendToPaymentDTO;
import labdsoft.park_bo_mcs.dtos.payment.PaymentsDTO;
import labdsoft.park_bo_mcs.models.park.*;
import labdsoft.park_bo_mcs.models.payment.PaymentHistory;
import labdsoft.park_bo_mcs.models.user.Customer;
import labdsoft.park_bo_mcs.models.user.Status;
import labdsoft.park_bo_mcs.models.user.Vehicle;
import labdsoft.park_bo_mcs.repositories.park.*;
import labdsoft.park_bo_mcs.repositories.payment.PaymentHistoryRepository;
import labdsoft.park_bo_mcs.repositories.user.CustomerRepository;
import labdsoft.park_bo_mcs.repositories.user.VehicleRepository;
import labdsoft.park_bo_mcs.rest.PaymentCommunication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        ParkingHistory parkingHistory = ParkingHistory.builder().customerID(vehicle.getCustomerID()).startTime(barrierLicenseReaderDTO.getDate()).endTime(barrierLicenseReaderDTO.getDate()).parkId(park.getParkID()).build();
        parkingHistoryRepository.save(parkingHistory);
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
        barrierDisplayDTO.setMessage("Have a nice day " + customer.getName() + "! Your total will be €!");

        return barrierDisplayDTO;
    }

    private Double getMoneyFromPayment(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        ParkingHistory parkingHistory = parkingHistoryRepository.findByCustomerIDLatest(vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()).getCustomerID());

        parkingHistory.setEndTime(barrierLicenseReaderDTO.getDate());
        parkingHistoryRepository.save(parkingHistory);

        SendToPaymentDTO sendToPaymentDTO = SendToPaymentDTO.builder().parkID(barrierLicenseReaderDTO.getParkID()).enterPark(parkingHistory.getStartTime())
                .leftPark(parkingHistory.getEndTime()).licensePlate(barrierLicenseReaderDTO.getPlateNumber()).build();

        PaymentsDTO paymentsDTO = paymentCommunication.postForPayment(sendToPaymentDTO);

        if (paymentsDTO != null)
            return paymentsDTO.getFinalPrice();
        else
            return 0.0;
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
