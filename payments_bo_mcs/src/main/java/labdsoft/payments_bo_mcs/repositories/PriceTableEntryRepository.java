package labdsoft.payments_bo_mcs.repositories;

import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceTableEntryRepository extends CrudRepository<PriceTableEntry, Long> {
    List<PriceTableEntry> findAllByParkId(Long parkId);
}

