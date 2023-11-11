package labdsoft.park_bo_mcs.repositories.user;

import labdsoft.park_bo_mcs.models.user.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, String> {
    Vehicle getVehicleByPlateNumber(String plateNumber);
}
