package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.OccupancyParkDTO;
import labdsoft.park_bo_mcs.models.park.Park;
import labdsoft.park_bo_mcs.models.park.Spot;
import labdsoft.park_bo_mcs.repositories.park.ParkRepository;
import labdsoft.park_bo_mcs.repositories.park.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Override
    public List<OccupancyParkDTO> getCurrentNumberOfAvailableSpotsInsideAllParks() {
        List<OccupancyParkDTO> listOccupancyParkDTO = new ArrayList<>();

        Iterable<Park> listPark = parkRepository.findAll();
        for(Park park : listPark){
            List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), false, true);
            OccupancyParkDTO occupancyParkDTO = OccupancyParkDTO.builder().parkid(park.getParkID()).parkNumber(park.getParkNumber()).occupancy(park.getMaxOcuppancy()).currentCapacity(listSpotsOccupied.size()).build();
            listOccupancyParkDTO.add(occupancyParkDTO);
        }

        return listOccupancyParkDTO;
    }
}
