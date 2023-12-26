package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.reporting.ParkReportDTO;
import labdsoft.park_bo_mcs.dtos.reporting.ParkTimeReportDTO;

import java.util.List;

public interface ParkReportService {
    List<ParkTimeReportDTO> getParkingTimeOfCustomers(Long parkId, String timePeriod);

    List<ParkReportDTO> getParkingReport(String vehicleType, String fuelType, Long parkId, String timePeriod);
}
