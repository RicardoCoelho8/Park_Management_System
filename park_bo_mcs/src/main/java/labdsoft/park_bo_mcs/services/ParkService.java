package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.NearbyParkOccupancyDTO;
import labdsoft.park_bo_mcs.dtos.park.OccupancyParkDTO;
import labdsoft.park_bo_mcs.dtos.park.ParkHistoryDTO;
import labdsoft.park_bo_mcs.dtos.park.PriceTableEntryDTO;

import java.util.List;

public interface ParkService {
    List<OccupancyParkDTO> getCurrentNumberOfAvailableSpotsInsideAllParks() throws Exception;

    List<PriceTableEntryDTO> getAllPriceTableEntriesById(Long parkId);

    List<NearbyParkOccupancyDTO> getRealTimeNearbyParksOccupancy(double latitude, double longitude, double maxDistanceKm);
    List<ParkHistoryDTO> getAllParkingHistoryByCustomerID(Long customerID);

    void createPark(String string);
}
