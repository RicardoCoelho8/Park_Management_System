package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.park.*;

import java.util.List;

public interface ParkService {
    List<OccupancyParkDTO> getCurrentNumberOfAvailableSpotsInsideAllParks() throws Exception;
    List<PriceTableEntryDTO> getAllPriceTableEntriesById(Long parkId);
    List<NearbyParkOccupancyDTO> getRealTimeNearbyParksOccupancy(double latitude, double longitude, double maxDistanceKm);
    List<ParkHistoryDTO> getAllParkingHistoryByCustomerID(Long customerID);
    String enableDisableSpot(SpotHistoryDTO spotHistoryDTO);
    String changeParkyThresholds(String parkNumber, ParkyConfigDTO parkyConfigDTO);
    String enableDisableOvernightFeeByParkNumber(OvernightEnableDTO dto);
    String changeOvernightFeePriceByParkNumber(OvernightPriceDTO dto);
    ParkyConfigDTO getParkyThresholds(String parkNumber);
    OvernightConfigDTO getOvernightConfigByParkNumber(String parkNumber);
    void createPark(String string);
    List<String> getAllParks();
    List<SpotDTO> getSpotsByParkNumber(String parkNumber);
    List<PriceTableEntryDTO> defineTimePeriods(String parkNumber, List<PriceTableEntryDTO> priceTableEntryDTO);
}
