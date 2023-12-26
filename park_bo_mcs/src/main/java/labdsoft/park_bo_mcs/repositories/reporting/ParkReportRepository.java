package labdsoft.park_bo_mcs.repositories.reporting;

import labdsoft.park_bo_mcs.models.reporting.ParkReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParkReportRepository extends CrudRepository<ParkReport, Long> {

    ParkReport findByDayAndMonthAndYearAndParkId(Integer day, Integer month, Integer year, Long parkId);

    List<ParkReport> findAllByParkId(Long parkId);
}
