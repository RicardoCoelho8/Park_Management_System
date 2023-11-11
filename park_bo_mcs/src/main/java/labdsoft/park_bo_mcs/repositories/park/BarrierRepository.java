package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.Barrier;
import org.springframework.data.repository.CrudRepository;

public interface BarrierRepository extends CrudRepository<Barrier, Long> {
    Barrier getBarrierByBarrierID(Long barrierID);
}

