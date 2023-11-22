package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.ParkingHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Calendar;
import java.util.List;

public interface ParkingHistoryRepository extends CrudRepository<ParkingHistory, Long> {

    @Query("SELECT p FROM ParkingHistory p WHERE p.endTime = p.startTime AND p.customerID = ?1")
    ParkingHistory findByCustomerIDLatest(Long customerID);

    List<ParkingHistory> findAllByStartTime(Calendar calendar);
}
