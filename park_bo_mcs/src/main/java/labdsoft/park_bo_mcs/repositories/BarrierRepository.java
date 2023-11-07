package labdsoft.park_bo_mcs.repositories;

import labdsoft.park_bo_mcs.model.Barrier;
import org.springframework.data.repository.CrudRepository;

public interface BarrierRepository extends CrudRepository<Barrier, Long> {
    Barrier getBarrierByBarrierID(Long barrierID);
}

