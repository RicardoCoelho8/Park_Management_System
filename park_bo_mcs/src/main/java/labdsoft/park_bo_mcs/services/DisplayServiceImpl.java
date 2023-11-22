package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayGetDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayUpdateDTO;
import labdsoft.park_bo_mcs.models.park.Display;
import labdsoft.park_bo_mcs.models.park.Park;
import labdsoft.park_bo_mcs.models.park.Spot;
import labdsoft.park_bo_mcs.models.park.State;
import labdsoft.park_bo_mcs.repositories.park.BarrierRepository;
import labdsoft.park_bo_mcs.repositories.park.DisplayRepository;
import labdsoft.park_bo_mcs.repositories.park.ParkRepository;
import labdsoft.park_bo_mcs.repositories.park.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayServiceImpl implements DisplayService {

    @Autowired
    private BarrierRepository barrierRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private DisplayRepository displayRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Override
    public BarrierDisplayDTO updateDisplayMessage(DisplayUpdateDTO displayUpdateDTO) {

        if (parkRepository.findByParkNumber(displayUpdateDTO.getParkNumber()) == null) {
            return displayUpdateDTO.toBarrierDisplayDTOFailure(displayUpdateDTO, "Park not found!");
        }

        if (barrierRepository.getBarrierByBarrierID(displayUpdateDTO.getBarrierID()) == null) {
            return displayUpdateDTO.toBarrierDisplayDTOFailure(displayUpdateDTO, "Barrier not found!");
        }

        List<Display> displayList = displayRepository.findAllByBarrierNumber(barrierRepository.getBarrierByBarrierID(displayUpdateDTO.getBarrierID()).getBarrierNumber());

        if (displayList.isEmpty()) {
            return displayUpdateDTO.toBarrierDisplayDTOFailure(displayUpdateDTO, "No displays found!");
        }

        for (Display display : displayList) {
            if (display.getState() == State.ACTIVE) {
                display.setMessage(displayUpdateDTO.getMessage());
            }
        }

        displayRepository.saveAll(displayList);

        return displayUpdateDTO.toBarrierDisplayDTO(displayUpdateDTO);
    }

    @Override
    public DisplayDTO getDisplayMessage(DisplayGetDTO displayGetDTO) {
        Display display = displayRepository.findByDisplayID(displayGetDTO.getDisplayID());

        if (display == null) {
            return DisplayDTO.builder().message("Display not found!").occupancy(0).build();
        }

        Park park = parkRepository.findByParkID(display.getParkID());

        List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), true, true);
        List<Spot> listSpotsOperacional = spotRepository.getSpotsByParkIDAndOperational(park.getParkID(), true);

        Integer occupancy = listSpotsOperacional.size() - listSpotsOccupied.size();

        display.setOccupancy(occupancy);
        displayRepository.save(display);

        return DisplayDTO.builder().message(display.getMessage()).occupancy(occupancy).build();
    }
}
