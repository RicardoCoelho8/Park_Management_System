package labdsoft.park_bo_mcs.repositories.park;

import labdsoft.park_bo_mcs.models.park.Spot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpotRepository extends CrudRepository<Spot, Long> {
    @Query("SELECT s FROM Spot s WHERE s.parkID=:parkID AND s.occupied=:occupied AND s.operational=:operational")
    List<Spot> getSpotsByParkIDAndOccupiedAndOperational(Long parkID, Boolean occupied, Boolean operational);
}
