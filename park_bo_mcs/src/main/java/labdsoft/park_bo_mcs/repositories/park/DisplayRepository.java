package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.Display;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DisplayRepository extends CrudRepository<Display, Long> {
    List<Display> findAllByDisplayNumber(String displayNumber);

    List<Display> findAllByBarrierNumber(String barrierNumber);

    Display findByDisplayID(Long displayID);
}

