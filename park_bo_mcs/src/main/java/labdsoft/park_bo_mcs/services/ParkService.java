package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.OccupancyParkDTO;
import labdsoft.park_bo_mcs.dtos.PriceTableEntryDTO;

import java.util.List;

public interface ParkService {
    List<OccupancyParkDTO> getCurrentNumberOfAvailableSpotsInsideAllParks();
    List<PriceTableEntryDTO> getAllPriceTableEntriesById(Long parkId);
}
