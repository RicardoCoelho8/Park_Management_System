package labdsoft.park_bo_mcs.repositories;

import labdsoft.park_bo_mcs.model.Spot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpotRepository extends CrudRepository<Spot, Long> {
    List<Spot> getSpotsByParkidAndOccupied(Long parkId, Boolean occupied);
}
