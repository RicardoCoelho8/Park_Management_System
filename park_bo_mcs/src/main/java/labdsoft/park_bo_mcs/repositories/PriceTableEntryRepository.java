package labdsoft.park_bo_mcs.repositories;

import labdsoft.park_bo_mcs.model.Barrier;
import labdsoft.park_bo_mcs.model.PriceTableEntry;
import org.springframework.data.repository.CrudRepository;

public interface PriceTableEntryRepository extends CrudRepository<PriceTableEntry, Long> {
}

