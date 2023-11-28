package labdsoft.park_bo_mcs.bootstrappers;

import labdsoft.park_bo_mcs.models.user.*;
import labdsoft.park_bo_mcs.repositories.user.CustomerRepository;
import labdsoft.park_bo_mcs.repositories.user.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
// @Profile("bootstrap")
public class CustomerBootstrapper implements CommandLineRunner {

    @Autowired
    private CustomerRepository cRepo;
    @Autowired
    private VehicleRepository vRepo;

    @Override
    public void run(String... args) {
        if (cRepo.count() != 0)
            return;
        values(4L, 501776508L, "Leo Kim", List.of("55-ND-41"));
        values(5L, 506779330L, "Maya Gupta", List.of("19-SJ-63", "AH-43-MR"));
        values(6L, 514884112L, "Rui Soares", List.of("46-ON-05", "32-VT-07", "28-XQ-51"));
    }

    private void createSampleVehicle(Long customerID, List<String> licensePlates) {
        List<Vehicle> vehicleList = new ArrayList<>();

        for (String licensePlate : licensePlates) {
            vehicleList.add(Vehicle.builder().customerID(customerID).plateNumber(licensePlate)
                    .vehicleType(VehicleType.AUTOMOBILE).vehicleEnergySource(VehicleEnergySource.FUEL).build());
        }

        vRepo.saveAll(vehicleList);
    }

    private void values(Long customerId, Long customerNif, String customerName, List<String> licensePlates) {
        if (cRepo.findByNif(customerNif) == null) {
            Customer customer = Customer.builder().nif(customerNif).name(customerName).status(Status.ENABLED)
                    .customerID(customerId).build();
            cRepo.save(customer);
            createSampleVehicle(customer.getCustomerID(), licensePlates);
        }
    }
}
