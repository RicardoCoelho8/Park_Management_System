package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.OccupancyParkDTO;
import labdsoft.park_bo_mcs.dtos.PriceTableEntryDTO;
import labdsoft.park_bo_mcs.models.park.Park;
import labdsoft.park_bo_mcs.models.park.PriceTableEntry;
import labdsoft.park_bo_mcs.models.park.Spot;
import labdsoft.park_bo_mcs.repositories.park.ParkRepository;
import labdsoft.park_bo_mcs.repositories.park.PriceTableEntryRepository;
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

    @Autowired
    private PriceTableEntryRepository priceTableEntryRepository;

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

    @Override
    public List<PriceTableEntryDTO> getAllPriceTableEntriesById(Long parkId) {
        List<PriceTableEntryDTO> listPriceTableEntryDTO = new ArrayList<>();

        List<PriceTableEntry> list = priceTableEntryRepository.findAllByParkId(parkId);

        for (PriceTableEntry priceTableEntry : list) {
            PriceTableEntryDTO priceTableEntryDTO = PriceTableEntryDTO.builder().parkId(priceTableEntry.getParkId()).periodStart(priceTableEntry.getPeriodStart()).periodEnd(priceTableEntry.getPeriodEnd()).thresholds(priceTableEntry.getThresholds()).build();
            listPriceTableEntryDTO.add(priceTableEntryDTO);
        }

        return listPriceTableEntryDTO;
    }
}
