package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.OccupancyParkDTO;

import java.util.List;

public interface ParkService {
    List<OccupancyParkDTO> getCurrentNumberOfAvailableSpotsInsideAllParks();
}
