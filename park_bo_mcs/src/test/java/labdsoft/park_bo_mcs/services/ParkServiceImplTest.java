package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.models.park.Location;
import labdsoft.park_bo_mcs.models.park.Park;
import labdsoft.park_bo_mcs.models.park.ParkyConfig;
import labdsoft.park_bo_mcs.models.park.Spot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkServiceImplTest {
    private static List<Spot> list_spots;

    @BeforeAll
    static void setUp() {
        list_spots = new ArrayList<>();

        list_spots.add(Spot.builder().spotNumber("A1").spotType(1).floorLevel("A").occupied(false).operational(false).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A2").spotType(1).floorLevel("A").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A3").spotType(1).floorLevel("A").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A4").spotType(1).floorLevel("A").occupied(true).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("A5").spotType(1).floorLevel("A").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B1").spotType(1).floorLevel("B").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B2").spotType(1).floorLevel("B").occupied(false).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B3").spotType(1).floorLevel("B").occupied(true).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B4").spotType(1).floorLevel("B").occupied(true).operational(true).parkID(1L).build());
        list_spots.add(Spot.builder().spotNumber("B5").spotType(1).floorLevel("B").occupied(false).operational(true).parkID(1L).build());
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
}