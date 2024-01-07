package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.reporting.ParkPercentageReportDTO;
import labdsoft.park_bo_mcs.dtos.reporting.ParkReportDTO;
import labdsoft.park_bo_mcs.dtos.reporting.ParkTimeReportDTO;

import java.util.List;

public interface ParkReportService {
    List<ParkTimeReportDTO> getParkingTimeOfCustomers(Long parkId, String timePeriod);

    ParkPercentageReportDTO getParkingReport(Long parkId);
}
