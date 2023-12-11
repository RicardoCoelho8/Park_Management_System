package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.*;
import labdsoft.park_bo_mcs.models.park.*;
import labdsoft.park_bo_mcs.repositories.park.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private ParkRepository parkRepository;

    @Autowired
    private ParkingHistoryRepository parkHistoryRepository;

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private SpotHistoryRepository spotHistoryRepository;

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
    public List<NearbyParkOccupancyDTO> getRealTimeNearbyParksOccupancy(double latitude, double longitude,
            double maxDistanceKm) {
        // assume nearby is at most 10 kms away

        List<NearbyParkOccupancyDTO> listNearbyParkOccupancy = new ArrayList<>();

        Iterable<Park> listPark = parkRepository.findAll();
        for (Park park : listPark) {
            double distance = this.calculateDistance(latitude, longitude, park.getLocation().getLatitude(),
                    park.getLocation().getLongitude());
            if (distance > maxDistanceKm) {
                continue;
            }

            List<Spot> listSpots = spotRepository.getSpotsByParkIDAndOccupiedAndOperational(park.getParkID(), false, true);

            List<SpotTypeOccupancyDTO> spotOccupanciesDto = Arrays.stream(SpotVehicleType.values())
                    .flatMap(spotVehicleType -> Arrays.stream(SpotType.values())
                            .map(spotType -> SpotTypeOccupancyDTO.builder()
                                    .spotVehicleType(spotVehicleType)
                                    .spotType(spotType)
                                    .availableSpots((int) listSpots.stream()
                                            .filter(spot -> spot.getSpotType() == spotType && spot.getSpotVehicleType() == spotVehicleType)
                                            .count())
                                    .build()))
                    .toList();

            listNearbyParkOccupancy.add(NearbyParkOccupancyDTO.builder()
                    .parkId(park.getParkID())
                    .distanceKm(distance)
                    .spotTypeOccupancies(spotOccupanciesDto)
                    .build());
        }

        return listNearbyParkOccupancy;
    }

    @Override
    public List<ParkHistoryDTO> getAllParkingHistoryByCustomerID(Long customerID) {
        List<ParkHistoryDTO> listParkHistoryDTO = new ArrayList<>();

        List<ParkingHistory> listParkHistory = parkHistoryRepository.findAllByCustomerID(customerID);
        for(ParkingHistory parkHistory : listParkHistory) {
            if(!parkHistory.getStartTime().toString().equalsIgnoreCase(parkHistory.getEndTime().toString())) {
                ParkHistoryDTO parkHistoryDTO = ParkHistoryDTO.builder()
                        .parkingHistoryId(parkHistory.getParkingHistoryId())
                        .startTime(parkHistory.getStartTime())
                        .endTime(parkHistory.getEndTime())
                        .parkId(parkHistory.getParkId())
                        .customerID(parkHistory.getCustomerID())
                        .hoursBetweenEntranceExit(parkHistory.getHoursBetweenEntranceExit())
                        .minutesBetweenEntranceExit(parkHistory.getMinutesBetweenEntranceExit())
                        .price(parkHistory.getPrice())
                        .build();

                listParkHistoryDTO.add(parkHistoryDTO);
            }
        }

        return listParkHistoryDTO;
    }

    @Override
    public String enableDisableSpot(SpotHistoryDTO spotHistoryDTO) {
        Park park = parkRepository.findByParkNumber(spotHistoryDTO.getParkNumber());

        Spot spot = spotRepository.getSpotBySpotNumberAndParkID(spotHistoryDTO.getSpotNumber(), park.getParkID());
        spot.setOperational(spotHistoryDTO.getOperational());
        spotRepository.save(spot);

        String status = spotHistoryDTO.getOperational() ? "enabled" : "disabled";
        String message = "Spot " + spotHistoryDTO.getSpotNumber() + " is now " + status + " and was changed by " + spotHistoryDTO.getManagerName();

        spotHistoryRepository.save(SpotHistory.builder()
                .spotNumber(spotHistoryDTO.getSpotNumber())
                .HistoryOperational(message)
                .build());

        return message;
    }

    @Override
    public void createPark(String string) {
        Park park = Park.builder().build();
        parkRepository.save(park);
    }

    @Override
    public List<String> getAllParks() {
        List<String> listParks = new ArrayList<>();

        Iterable<Park> listPark = parkRepository.findAll();
        for (Park park : listPark) {
            listParks.add(park.getParkNumber().toString());
        }

        return listParks;
    }

    @Override
    public List<SpotDTO> getSpotsByParkNumber(String parkNumber) {
        List<SpotDTO> listSpotDTO = new ArrayList<>();

        Park park = parkRepository.findByParkNumber(Long.parseLong(parkNumber));

        List<Spot> listSpots = spotRepository.getSpotsByParkID(park.getParkID());
        for (Spot spot : listSpots) {
            SpotDTO spotDTO = SpotDTO.builder()
                    .spotNumber(spot.getSpotNumber())
                    .spotType(spot.getSpotType())
                    .spotVehicleType(spot.getSpotVehicleType())
                    .floorLevel(spot.getFloorLevel())
                    .operational(spot.isOperational())
                    .occupied(spot.isOccupied())
                    .build();

            listSpotDTO.add(spotDTO);
        }

        return listSpotDTO;
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
