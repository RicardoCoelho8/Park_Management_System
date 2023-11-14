package labdsoft.park_bo_mcs.repositories.user;

import labdsoft.park_bo_mcs.models.user.ParkingHistory;
import org.springframework.data.repository.CrudRepository;

public interface ParkingHistoryRepository extends CrudRepository<ParkingHistory, Long> {

    ParkingHistory findByEndTimeEmpty();

}
