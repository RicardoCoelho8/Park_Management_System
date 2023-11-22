package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.Barrier;
import labdsoft.park_bo_mcs.models.park.Display;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BarrierRepository extends CrudRepository<Barrier, Long> {
    List<Barrier> findAllByBarrierNumber(String barrierNumber);

    Barrier getBarrierByBarrierID(Long barrierID);
}

