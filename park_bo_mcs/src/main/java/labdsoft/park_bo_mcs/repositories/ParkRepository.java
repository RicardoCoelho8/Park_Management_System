package labdsoft.park_bo_mcs.repositories;

import labdsoft.park_bo_mcs.model.Park;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ParkRepository extends CrudRepository<Park, Long> {
    @Query("SELECT p FROM Park p WHERE p.parkNumber = ?1")
    Optional<Park> findByParkNumber(Long parkNumber);
}

