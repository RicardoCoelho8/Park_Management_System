package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.NearbyParkOccupancyDTO;
import labdsoft.park_bo_mcs.dtos.OccupancyParkDTO;
import labdsoft.park_bo_mcs.dtos.PriceTableEntryDTO;

import java.util.List;

public interface ParkService {
    List<OccupancyParkDTO> getCurrentNumberOfAvailableSpotsInsideAllParks();
    List<PriceTableEntryDTO> getAllPriceTableEntriesById(Long parkId);
    List<NearbyParkOccupancyDTO> getRealTimeNearbyParksOccupancy(double latitude, double longitude, double maxDistanceKm);

}
