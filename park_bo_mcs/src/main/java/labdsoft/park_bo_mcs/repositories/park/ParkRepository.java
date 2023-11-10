package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.model.park.Park;
import org.springframework.data.repository.CrudRepository;

public interface ParkRepository extends CrudRepository<Park, Long> {
    Park findByParkNumber(Long parkNumber);
}

