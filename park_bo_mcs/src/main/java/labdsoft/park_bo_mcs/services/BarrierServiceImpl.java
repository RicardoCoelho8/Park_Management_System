package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dtos.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.models.park.Park;
import labdsoft.park_bo_mcs.models.park.Spot;
import labdsoft.park_bo_mcs.models.park.State;
import labdsoft.park_bo_mcs.models.user.User;
import labdsoft.park_bo_mcs.repositories.park.BarrierRepository;
import labdsoft.park_bo_mcs.repositories.park.ParkRepository;
import labdsoft.park_bo_mcs.repositories.park.SpotRepository;
import labdsoft.park_bo_mcs.repositories.user.UserRepository;
import labdsoft.park_bo_mcs.repositories.user.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BarrierServiceImpl implements BarrierService {


    @Autowired
    private BarrierRepository barrierRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Override
    public BarrierDisplayDTO entranceOpticalReader(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        BarrierDisplayDTO barrierDisplayDTO = barrierLicenseReaderDTO.toBarrierDisplayDTO(barrierLicenseReaderDTO);

        if (vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()) != null
                && barrierRepository.getBarrierByBarrierID(barrierLicenseReaderDTO.getBarrierID()) != null
                && parkRepository.findByParkNumber(barrierLicenseReaderDTO.getParkNumber()) != null) {

            Park park = parkRepository.findByParkNumber(barrierLicenseReaderDTO.getParkNumber());

            List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), true, true);

            if (park.getMaxOcuppancy() > listSpotsOccupied.size()
                    && barrierRepository.getBarrierByBarrierID(barrierLicenseReaderDTO.getBarrierID()).getState() == State.ACTIVE) {

                List<Spot> listSpotsOpen = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), false, true);

                Random rand = new Random();

                Spot spot = listSpotsOpen.get(rand.nextInt(listSpotsOpen.size()));
                spot.setOccupied(true);
                spotRepository.save(spot);

                barrierDisplayDTO.setSuccess(true);
            }
        }

        if (barrierDisplayDTO.getSuccess()) {
            User user = userRepository.getUserByUserID(vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()).getUserID());

            barrierDisplayDTO.setMessage("Have a nice day " + user.getName() + "!");
        } else {
            barrierDisplayDTO.setMessage("Please use the Park20 app to register and use the parking. Or use the QR code to download the app.");
        }

        return barrierDisplayDTO;
    }

    @Override
    public BarrierDisplayDTO exitOpticalReader(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        if (vehicleRepository.getVehicleByPlateNumber(barrierLicenseReaderDTO.getPlateNumber()) != null
                && barrierRepository.getBarrierByBarrierID(barrierLicenseReaderDTO.getBarrierID()) != null
                && parkRepository.findByParkNumber(barrierLicenseReaderDTO.getParkNumber()) != null) {

            Park park = parkRepository.findByParkNumber(barrierLicenseReaderDTO.getParkNumber());

            if (barrierRepository.getBarrierByBarrierID(barrierLicenseReaderDTO.getBarrierID()).getState() == State.ACTIVE) {

                List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), true, true);

                Random rand = new Random();
                Spot spot = listSpotsOccupied.get(rand.nextInt(listSpotsOccupied.size()));
                spot.setOccupied(false);

                spotRepository.save(spot);
            }
        }

        return null;
    }
}
