package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.*;

import java.util.List;

public interface ParkService {
    List<OccupancyParkDTO> getCurrentNumberOfAvailableSpotsInsideAllParks() throws Exception;

    List<PriceTableEntryDTO> getAllPriceTableEntriesById(Long parkId);

    List<NearbyParkOccupancyDTO> getRealTimeNearbyParksOccupancy(double latitude, double longitude, double maxDistanceKm);
    List<ParkHistoryDTO> getAllParkingHistoryByCustomerID(Long customerID);
    String enableDisableSpot(SpotHistoryDTO spotHistoryDTO);

    void createPark(String string);
}
