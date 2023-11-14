package labdsoft.payments_bo_mcs.repositories;

import labdsoft.payments_bo_mcs.model.payment.Payments;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    @Query("SELECT p FROM Vehicle p WHERE p.licensePlateNumber=:licensePlate")
    Optional<Vehicle> findByLicensePlate(String licensePlate);

}

