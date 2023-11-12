package labdsoft.park_bo_mcs.bootstrappers;

import labdsoft.park_bo_mcs.models.park.*;
import labdsoft.park_bo_mcs.repositories.park.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
//@Profile("bootstrap")
public class ParkBootstrapper implements CommandLineRunner {

    @Autowired
    private ParkRepository pRepo;
    @Autowired
    private SpotRepository sRepo;
    @Autowired
    private BarrierRepository bRepo;
    @Autowired
    private DisplayRepository dRepo;
    @Autowired
    private PriceTableEntryRepository ptRepo;

    @Override
    public void run(String... args) {
        values(1L, 10, createSampleParkyConfig(), Location.builder().latitude(40.36841685539075).longitude(-8.659655996430294).build());
        values(2L, 10, createSampleParkyConfig(), Location.builder().latitude(41.15932631626018).longitude(-8.659665314733802).build());
        values(3L, 10, createSampleParkyConfig(), Location.builder().latitude(40.63197951206417).longitude(-8.631234173497187).build());
        values(4L, 10, createSampleParkyConfig(), Location.builder().latitude(41.538183003885216).longitude(-8.431420332359862).build());
        values(5L, 10, createSampleParkyConfig(), Location.builder().latitude(41.17643631180696).longitude(-8.55806931330527).build());
    }

    private void createSampleSpots(Long id) {
        List<Spot> list_spots = new ArrayList<>();

        list_spots.add(Spot.builder().spotNumber("A1").spotType(1).floorLevel("A").occupied(false).operational(false).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("A2").spotType(1).floorLevel("A").occupied(false).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("A3").spotType(1).floorLevel("A").occupied(false).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("A4").spotType(1).floorLevel("A").occupied(true).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("A5").spotType(1).floorLevel("A").occupied(false).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("B1").spotType(1).floorLevel("B").occupied(false).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("B2").spotType(1).floorLevel("B").occupied(false).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("B3").spotType(1).floorLevel("B").occupied(true).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("B4").spotType(1).floorLevel("B").occupied(true).operational(true).parkID(id).build());
        list_spots.add(Spot.builder().spotNumber("B5").spotType(1).floorLevel("B").occupied(false).operational(true).parkID(id).build());

        sRepo.saveAll(list_spots);
    }

    private ParkyConfig createSampleParkyConfig() {
        return ParkyConfig.builder().parkiesPerHour(15).parkiesPerMinute(1).build();
    }

    private void createSampleBarriers(Long id) {
        List<Barrier> list_barriers = new ArrayList<>();

        list_barriers.add(Barrier.builder().barrierNumber("B1.1").state(State.ACTIVE).parkID(id).build());
        list_barriers.add(Barrier.builder().barrierNumber("B1.2").state(State.ACTIVE).parkID(id).build());
        list_barriers.add(Barrier.builder().barrierNumber("B2.1").state(State.ACTIVE).parkID(id).build());
        list_barriers.add(Barrier.builder().barrierNumber("B2.2").state(State.ACTIVE).parkID(id).build());
        list_barriers.add(Barrier.builder().barrierNumber("B3.1").state(State.DISABLED).parkID(id).build());
        list_barriers.add(Barrier.builder().barrierNumber("B3.2").state(State.DISABLED).parkID(id).build());

        bRepo.saveAll(list_barriers);
    }

    private void createSampleDisplays(Long id) {
        List<Display> list_displays = new ArrayList<>();

        list_displays.add(Display.builder().displayNumber("D1.1").state(State.ACTIVE).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D1.2").state(State.ACTIVE).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D1.3").state(State.ACTIVE).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D2.1").state(State.ACTIVE).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D2.2").state(State.ACTIVE).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D2.3").state(State.ACTIVE).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D3.1").state(State.DISABLED).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D3.2").state(State.DISABLED).parkID(id).build());
        list_displays.add(Display.builder().displayNumber("D3.3").state(State.DISABLED).parkID(id).build());

        dRepo.saveAll(list_displays);
    }

    private List<ThresholdCost> createSampleThresholdCosts() {
        List<ThresholdCost> list_thresholdCosts = new ArrayList<>();

        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.30).costPerMinuteMotorcycles(0.30).build());

        return list_thresholdCosts;
    }

    private void createSamplePriceTableEntry(Long id) {
        List<PriceTableEntry> list_priceTableEntry = new ArrayList<>();

        list_priceTableEntry.add(PriceTableEntry.builder().periodStart("9:00").periodEnd("21:00").thresholds(createSampleThresholdCosts()).parkId(id).build());
        list_priceTableEntry.add(PriceTableEntry.builder().periodStart("21:00").periodEnd("9:00").thresholds(createSampleThresholdCosts()).parkId(id).build());

        ptRepo.saveAll(list_priceTableEntry);
    }

    private void values(Long parkNumber, int maxOcuppancy, ParkyConfig parkyConfig, Location location) {
        if (pRepo.findByParkNumber(parkNumber) == null) {
            Park p1 = Park.builder().parkNumber(parkNumber).maxOcuppancy(maxOcuppancy).parkyConfig(parkyConfig).location(location).build();
            pRepo.save(p1);
            createSampleSpots(p1.getParkID());
            createSampleBarriers(p1.getParkID());
            createSampleDisplays(p1.getParkID());
            createSamplePriceTableEntry(p1.getParkID());
        }
    }
}
