package labdsoft.payments_bo_mcs.repositories;

import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    @Query("SELECT p FROM Vehicle p WHERE p.plateNumber=:licensePlate")
    Optional<Vehicle> findByLicensePlate(String licensePlate);

}

