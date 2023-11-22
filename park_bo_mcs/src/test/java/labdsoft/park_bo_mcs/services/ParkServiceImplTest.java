package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.models.park.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkServiceImplTest {
    private static List<Spot> list_spots;
    private static List<PriceTableEntry> list_priceTableEntry;

    @BeforeAll
    static void setUp() {
        list_spots = new ArrayList<>();
        list_priceTableEntry = new ArrayList<>();

        list_spots.add(Spot.builder().spotNumber("A1").spotType(SpotType.ELECTRIC).floorLevel("A").occupied(false).operational(false).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A2").spotType(SpotType.ELECTRIC).floorLevel("A").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A3").spotType(SpotType.ELECTRIC).floorLevel("A").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A4").spotType(SpotType.ELECTRIC).floorLevel("A").occupied(true).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A5").spotType(SpotType.ELECTRIC).floorLevel("A").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B1").spotType(SpotType.ELECTRIC).floorLevel("B").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B2").spotType(SpotType.ELECTRIC).floorLevel("B").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B3").spotType(SpotType.ELECTRIC).floorLevel("B").occupied(true).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B4").spotType(SpotType.ELECTRIC).floorLevel("B").occupied(true).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B5").spotType(SpotType.ELECTRIC).floorLevel("B").occupied(false).operational(true).parkID(1L).build());

        list_priceTableEntry.add(PriceTableEntry.builder().periodStart("9:00").periodEnd("21:00").thresholds(createSampleThresholdCosts()).build());
        list_priceTableEntry.add(PriceTableEntry.builder().periodStart("21:00").periodEnd("9:00").thresholds(createSampleThresholdCosts()).build());
    }

    private static List<ThresholdCost> createSampleThresholdCosts() {
        List<ThresholdCost> list_thresholdCosts = new ArrayList<>();

        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.15).costPerMinuteMotorcycles(0.15).build());
        list_thresholdCosts.add(ThresholdCost.builder().thresholdMinutes(15).costPerMinuteAutomobiles(0.30).costPerMinuteMotorcycles(0.30).build());

        return list_thresholdCosts;
    }

    @Test
    void getCurrentNumberOfAvailableSpotsInsideAllParks() {
        List<Spot> listSpotsAvailable = new ArrayList<>();
        for(Spot spot : list_spots){
            if(!spot.isOccupied() && spot.isOperational()){
                listSpotsAvailable.add(spot);
            }
        }
        assertEquals(6, listSpotsAvailable.size());
    }

    @Test
    void getAllPriceTableEntries() {
        assertEquals(2, list_priceTableEntry.size());
    }

    @Test
    void getAllThresholdCosts() {
        for (PriceTableEntry priceTableEntry : list_priceTableEntry) {
            assertEquals(5, priceTableEntry.getThresholds().size());
        }
    }
}