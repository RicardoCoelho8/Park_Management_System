package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.ParkMcsApplication;
import labdsoft.park_bo_mcs.dtos.*;
import labdsoft.park_bo_mcs.models.park.*;
import labdsoft.park_bo_mcs.models.user.*;
import labdsoft.park_bo_mcs.repositories.park.BarrierRepository;
import labdsoft.park_bo_mcs.repositories.park.DisplayRepository;
import labdsoft.park_bo_mcs.repositories.park.ParkRepository;
import labdsoft.park_bo_mcs.repositories.park.SpotRepository;
import labdsoft.park_bo_mcs.repositories.user.CustomerRepository;
import labdsoft.park_bo_mcs.repositories.user.VehicleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
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
    void remove() {
        Park p = pRepo.findByParkNumber(9999999L);
        valuesRemove();
        pRepo.delete(p);
    }

    private void valuesRemove() {
        removeSampleSpot();
        removeSampleBarrier();
        removeSampleDisplay();
        removeSampleVehicle();
        removeSampleCustomer();
    }

    private void removeSampleVehicle() {
        vRepo.delete(vRepo.getVehicleByPlateNumber("AZ07ZL"));
    }

    private void removeSampleCustomer() {
        cRepo.delete(cRepo.findByNif(123L));
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
    void enteringThePark() {
        List<Barrier> barrierList = bRepo.findAllByBarrierNumber("TestB");
        Park park = pRepo.findByParkNumber(9999999L);

        for (Barrier barrier : barrierList) {
            BarrierDisplayDTO barrierDisplayDTO = service.entranceOpticalReader(BarrierLicenseReaderDTO.builder().barrierID(barrier.getBarrierID()).parkID(park.getParkID()).parkNumber(9999999L).plateNumber("AZ07ZL").date(Calendar.getInstance()).build());

            assertEquals("Welcome to the park TestC!", barrierDisplayDTO.getMessage());
        }
    }

//    @Test
//    void exitingThePark() {
//        List<Display> displayList = dRepo.findAllByDisplayNumber("TestD");
//
//        for (Display display : displayList) {
//            DisplayGetDTO displayGetDTO = new DisplayGetDTO(display.getDisplayID());
//
//            DisplayDTO displayDTO = service.getDisplayMessage(displayGetDTO);
//
//            assertNull(displayDTO.getMessage());
//            assertEquals(1, displayDTO.getOccupancy());
//        }
//    }
}