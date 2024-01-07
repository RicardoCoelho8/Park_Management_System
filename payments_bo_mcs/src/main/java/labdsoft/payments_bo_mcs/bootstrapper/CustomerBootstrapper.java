package labdsoft.payments_bo_mcs.bootstrapper;

import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleEnergySource;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleType;
import labdsoft.payments_bo_mcs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//@Profile("bootstrap")
public class CustomerBootstrapper implements CommandLineRunner {

    @Autowired
    private UserRepository uRepo;

    @Override
    public void run(String... args) {
        values(501776508L, List.of("55-ND-41"));
        values(506779330L, List.of("19-SJ-63", "AH-43-MR"));
        values(514884112L, List.of("46-ON-05", "32-VT-07", "28-XQ-51"));
    }

    private List<Vehicle> createSampleVehicle(List<String> licensePlates) {
        List<Vehicle> vehicleList = new ArrayList<>();

        for (String licensePlate : licensePlates) {
            vehicleList.add(Vehicle.builder().plateNumber(licensePlate).vehicleType(VehicleType.AUTOMOBILE).vehicleEnergySource(VehicleEnergySource.FUEL).build());
        }

        return vehicleList;
    }

    private void values(Long customerNif, List<String> licensePlates) {
        if (uRepo.findByNif(customerNif).isEmpty()) {
            AppUser customer = AppUser.builder().nif(customerNif).vehicles(createSampleVehicle(licensePlates)).totalParkies(50).build();
            uRepo.save(customer);
        }
    }
}
