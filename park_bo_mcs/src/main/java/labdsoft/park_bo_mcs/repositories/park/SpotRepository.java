package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.Spot;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpotRepository extends CrudRepository<Spot, Long> {
    List<Spot> getSpotsByParkIDAndOccupiedAndOperational(Long parkID, Boolean occupied, Boolean operational);

    List<Spot> getSpotsByParkIDAndOperational(Long parkID, Boolean operational);
}
