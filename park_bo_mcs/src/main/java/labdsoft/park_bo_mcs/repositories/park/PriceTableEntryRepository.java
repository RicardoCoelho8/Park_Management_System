package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.PriceTableEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceTableEntryRepository extends CrudRepository<PriceTableEntry, Long> {
    List<PriceTableEntry> findAllByParkId(Long parkId);
}

