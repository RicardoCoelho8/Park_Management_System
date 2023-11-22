package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.ParkMcsApplication;
import labdsoft.park_bo_mcs.dtos.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.DisplayDTO;
import labdsoft.park_bo_mcs.dtos.DisplayGetDTO;
import labdsoft.park_bo_mcs.dtos.DisplayUpdateDTO;
import labdsoft.park_bo_mcs.models.park.*;
import labdsoft.park_bo_mcs.repositories.park.BarrierRepository;
import labdsoft.park_bo_mcs.repositories.park.DisplayRepository;
import labdsoft.park_bo_mcs.repositories.park.ParkRepository;
import labdsoft.park_bo_mcs.repositories.park.SpotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = ParkMcsApplication.class)
class DisplayServiceImplTest {

    @Autowired
    private DisplayService service;

    @Autowired
    private DisplayRepository dRepo;

    @Autowired
    private BarrierRepository bRepo;

    @Autowired
    private ParkRepository pRepo;

    @Autowired
    private SpotRepository sRepo;

    @BeforeEach
    void setUp() {
        values(createSampleParkyConfig(), Location.builder().latitude(40.36841685539075).longitude(-8.659655996430294).build());
    }

    private void values(ParkyConfig parkyConfig, Location location) {
        if (pRepo.findByParkNumber(9999999L) == null) {
            Park p1 = Park.builder().parkNumber(9999999L).maxOcuppancy(1).parkyConfig(parkyConfig).location(location).build();
            pRepo.save(p1);
            createSampleSpot(p1.getParkID());
            createSampleBarrier(p1.getParkID());
            createSampleDisplay(p1.getParkID());
        }
    }

    private ParkyConfig createSampleParkyConfig() {
        return ParkyConfig.builder().parkiesPerHour(15).parkiesPerMinute(1).build();
    }

    private void createSampleSpot(Long id) {
        sRepo.save(Spot.builder().spotNumber("TestS").spotType(SpotType.FUEL).floorLevel("A").occupied(false).operational(true).parkID(id).build());
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
    void updateDisplayMessage() {
        List<Barrier> barrierList = bRepo.findAllByBarrierNumber("TestB");
        Park park = pRepo.findByParkNumber(9999999L);

        for (Barrier barrier : barrierList) {
            BarrierDisplayDTO barrierDisplayDTO = service.updateDisplayMessage(DisplayUpdateDTO.builder().barrierID(barrier.getBarrierID()).parkID(park.getParkID()).parkNumber(9999999L).message("TestM").build());

            assertEquals("TestM", barrierDisplayDTO.getMessage());
        }
    }

    @Test
    void getDisplayMessage() {
        List<Display> displayList = dRepo.findAllByDisplayNumber("TestD");

        for (Display display : displayList) {
            DisplayGetDTO displayGetDTO = new DisplayGetDTO(display.getDisplayID());

            DisplayDTO displayDTO = service.getDisplayMessage(displayGetDTO);

            assertNull(displayDTO.getMessage());
            assertEquals(1, displayDTO.getOccupancy());
        }
    }
}