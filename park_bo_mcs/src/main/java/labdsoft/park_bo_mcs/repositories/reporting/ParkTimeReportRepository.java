package labdsoft.park_bo_mcs.repositories.reporting;

import labdsoft.park_bo_mcs.models.reporting.ParkTimeReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkTimeReportRepository extends CrudRepository<ParkTimeReport, Long> {
    List<ParkTimeReport> findAllByParkReportId(Long parkReportId);
}
