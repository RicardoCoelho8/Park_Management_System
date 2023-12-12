package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.PriceTableEntry;
import labdsoft.park_bo_mcs.models.park.ThresholdCost;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PriceTableEntryRepository extends CrudRepository<PriceTableEntry, Long> {

    @Query("SELECT p FROM PriceTableEntry p WHERE p.parkId =:parkId")
    List<PriceTableEntry> findAllByParkId(Long parkId);

    @Query("SELECT p FROM PriceTableEntry p WHERE p.parkId =:parkId")
    List<PriceTableEntry> getAllByParkId(@Param("parkId")Long parkId);

    @Modifying
    @Query("Delete FROM PriceTableEntry WHERE parkId =:parkId")
    int deleteAllByParkId(@Param("parkId") Long parkId);
}

