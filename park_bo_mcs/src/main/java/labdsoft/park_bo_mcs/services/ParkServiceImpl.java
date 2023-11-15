package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.communications.Publish;
import labdsoft.park_bo_mcs.dtos.NearbyParkOccupancyDTO;
import labdsoft.park_bo_mcs.dtos.OccupancyParkDTO;
import labdsoft.park_bo_mcs.dtos.PriceTableEntryDTO;
import labdsoft.park_bo_mcs.models.park.Park;
import labdsoft.park_bo_mcs.models.park.PriceTableEntry;
import labdsoft.park_bo_mcs.models.park.Spot;
import labdsoft.park_bo_mcs.repositories.park.ParkRepository;
import labdsoft.park_bo_mcs.repositories.park.PriceTableEntryRepository;
import labdsoft.park_bo_mcs.repositories.park.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {

    @Value("${spring.rabbitmq.host}")
    private String host;

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
        for (Park park : listPark) {
            List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(),
                    false, true);
            OccupancyParkDTO occupancyParkDTO = OccupancyParkDTO.builder().parkid(park.getParkID())
                    .parkNumber(park.getParkNumber()).occupancy(park.getMaxOcuppancy())
                    .currentCapacity(listSpotsOccupied.size()).build();
            listOccupancyParkDTO.add(occupancyParkDTO);
        }

        return listOccupancyParkDTO;
    }

    @Override
    public List<PriceTableEntryDTO> getAllPriceTableEntriesById(Long parkId) {
        List<PriceTableEntryDTO> listPriceTableEntryDTO = new ArrayList<>();

        List<PriceTableEntry> list = priceTableEntryRepository.findAllByParkId(parkId);

        for (PriceTableEntry priceTableEntry : list) {
            PriceTableEntryDTO priceTableEntryDTO = PriceTableEntryDTO.builder().parkId(priceTableEntry.getParkId())
                    .periodStart(priceTableEntry.getPeriodStart()).periodEnd(priceTableEntry.getPeriodEnd())
                    .thresholds(priceTableEntry.getThresholds()).build();
            listPriceTableEntryDTO.add(priceTableEntryDTO);
        }

        return listPriceTableEntryDTO;
    }

    @Override
    public List<NearbyParkOccupancyDTO> getRealTimeNearbyParksOccupancy(double latitude, double longitude, double maxDistanceKm) {
        // assume nearby is at most 10 kms away

        List<NearbyParkOccupancyDTO> listNearbyParkOccupancy = new ArrayList<>();

        Iterable<Park> listPark = parkRepository.findAll();
        for (Park park : listPark) {
            double distance = this.calculateDistance(latitude, longitude, park.getLocation().getLatitude(),
                    park.getLocation().getLongitude());
            if (distance > maxDistanceKm) {
                continue;
            }
            List<Spot> listSpotsOccupied = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(),
                    false, true);
            listNearbyParkOccupancy.add(NearbyParkOccupancyDTO.builder().parkId(park.getParkID())
                    .parkNumber(park.getParkNumber()).occupancy(park.getMaxOcuppancy())
                    .currentCapacity(listSpotsOccupied.size()).distanceKm(distance).build());
        }

       return listNearbyParkOccupancy;
    }

    @Override
    public void createPark(String string) {
        Park park = Park.builder().build();
        parkRepository.save(park);
    }

    // https://www.geodatasource.com/developers/java
    // doesn't take into consideration height, and a straight line distance
    // isn't taking into account street routes etc
    // verified with other sites
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515 * 1.609344; // KM
        return dist;
    }

}
