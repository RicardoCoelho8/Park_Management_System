package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.ParkMcsApplication;
import labdsoft.park_bo_mcs.dtos.park.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.models.park.*;
import labdsoft.park_bo_mcs.models.user.*;
import labdsoft.park_bo_mcs.repositories.park.*;
import labdsoft.park_bo_mcs.repositories.user.CustomerRepository;
import labdsoft.park_bo_mcs.repositories.user.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ParkMcsApplication.class)
class BarrierServiceImplTest {

    @Autowired
    private BarrierService service;

    @Autowired
    private DisplayRepository dRepo;

    @Autowired
    private BarrierRepository bRepo;

    @Autowired
    private ParkRepository pRepo;

    @Autowired
    private SpotRepository sRepo;

    @Autowired
    private CustomerRepository cRepo;

    @Autowired
    private VehicleRepository vRepo;

    @Autowired
    private ParkingHistoryRepository phRepo;

    @BeforeEach
    void setUp() {
        values(createSampleParkyConfig(), Location.builder().latitude(40.36841685539075).longitude(-8.659655996430294).build(), List.of("AZ07ZL"));
    }

    private void values(ParkyConfig parkyConfig, Location location, List<String> licensePlates) {
        if (pRepo.findByParkNumber(9999999L) == null) {
            Park p1 = Park.builder().parkNumber(9999999L).maxOcuppancy(1).parkyConfig(parkyConfig).location(location).build();
            pRepo.save(p1);
            createSampleSpot(p1.getParkID());
            createSampleBarrier(p1.getParkID());
            createSampleDisplay(p1.getParkID());
        }

        if (cRepo.findByNif(123L) == null) {
            Customer customer = Customer.builder().nif(123L).name("TestC").status(Status.ACTIVE).build();
            cRepo.save(customer);
            createSampleVehicle(customer.getCustomerID(), licensePlates);
        }
    }

    private void createSampleVehicle(Long customerID, List<String> licensePlates) {
        List<Vehicle> vehicleList = new ArrayList<>();

        for (String licensePlate: licensePlates) {
            vehicleList.add(Vehicle.builder().customerID(customerID).plateNumber(licensePlate).vehicleType(VehicleType.AUTOMOBILE).vehicleEnergySource(VehicleEnergySource.FUEL).build());
        }

        vRepo.saveAll(vehicleList);
    }

    private ParkyConfig createSampleParkyConfig() {
        return ParkyConfig.builder().parkiesPerHour(15).parkiesPerMinute(1).build();
    }

    private void createSampleSpot(Long id) {
        sRepo.save(Spot.builder().spotNumber("TestS").spotType(SpotType.FUEL).spotVehicleType(SpotVehicleType.AUTOMOBILE).floorLevel("A").occupied(false).operational(true).parkID(id).build());
    }

    private void createSampleBarrier(Long id) {
        bRepo.save(Barrier.builder().barrierNumber("TestB").state(State.ACTIVE).parkID(id).build());
    }

    private void createSampleDisplay(Long id) {
        dRepo.save(Display.builder().displayNumber("TestD").barrierNumber("TestB").state(State.ACTIVE).parkID(id).build());
    }

    @AfterEach
    void remove() throws ParseException {
        Park p = pRepo.findByParkNumber(9999999L);
        valuesRemove();
        pRepo.delete(p);
    }

    private void valuesRemove() throws ParseException {
        removeSampleSpot();
        removeSampleBarrier();
        removeSampleDisplay();
        removeSampleVehicle();
        removeSampleCustomer();
        removeParkingHistory();
    }

    private void removeSampleVehicle() {
        vRepo.delete(vRepo.getVehicleByPlateNumber("AZ07ZL"));
    }

    private void removeSampleCustomer() {
        cRepo.delete(cRepo.findByNif(123L));
    }

    private void removeParkingHistory() throws ParseException {
        String dateString = "2001-01-01 11:22:59.563440";

        // The format should match the string format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        // Parse the date string into a Date object
        Date date = sdf.parse(dateString);

        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Set the Calendar time to the parsed Date
        calendar.setTime(date);

        phRepo.deleteAll(phRepo.findAllByStartTime(calendar));
    }

    private void removeSampleSpot() {
        sRepo.deleteAll(sRepo.findAllBySpotNumber("TestS"));
    }

    private void removeSampleBarrier() {
        bRepo.deleteAll(bRepo.findAllByBarrierNumber("TestB"));
    }

    private void removeSampleDisplay() {
        dRepo.deleteAll(dRepo.findAllByDisplayNumber("TestD"));
    }

    @Test
    void enteringThePark() throws ParseException {
        List<Barrier> barrierList = bRepo.findAllByBarrierNumber("TestB");
        Park park = pRepo.findByParkNumber(9999999L);

        for (Barrier barrier : barrierList) {
            String dateString = "2001-01-01 11:22:59.563440";

            // The format should match the string format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            // Parse the date string into a Date object
            Date date = sdf.parse(dateString);

            // Create a Calendar instance
            Calendar calendar = Calendar.getInstance();

            // Set the Calendar time to the parsed Date
            calendar.setTime(date);

            BarrierDisplayDTO barrierDisplayDTO = service.entranceOpticalReader(BarrierLicenseReaderDTO.builder().barrierID(barrier.getBarrierID()).parkID(park.getParkID()).parkNumber(9999999L).plateNumber("AZ07ZL").date(calendar).build());

            assertEquals("Welcome to the park TestC!", barrierDisplayDTO.getMessage());
        }
    }

    @Test
    void exitingThePark() throws ParseException {
        List<Barrier> barrierList = bRepo.findAllByBarrierNumber("TestB");
        Park park = pRepo.findByParkNumber(9999999L);

        for (Barrier barrier : barrierList) {
            String dateString = "2001-01-01 11:22:59.563440";

            // The format should match the string format
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            // Parse the date string into a Date object
            Date date = sdf.parse(dateString);

            // Create a Calendar instance
            Calendar calendar = Calendar.getInstance();

            // Set the Calendar time to the parsed Date
            calendar.setTime(date);

            service.entranceOpticalReader(BarrierLicenseReaderDTO.builder().barrierID(barrier.getBarrierID()).parkID(park.getParkID()).parkNumber(9999999L).plateNumber("AZ07ZL").date(calendar).build());

            calendar.add(Calendar.HOUR, 1);

            BarrierDisplayDTO barrierDisplayDTO = service.exitOpticalReader(BarrierLicenseReaderDTO.builder().barrierID(barrier.getBarrierID()).parkID(park.getParkID()).parkNumber(9999999L).plateNumber("AZ07ZL").date(Calendar.getInstance()).build());

            assertEquals("Have a nice day TestC! Your total will be", barrierDisplayDTO.getMessage().substring(0, 41));
        }
    }
}