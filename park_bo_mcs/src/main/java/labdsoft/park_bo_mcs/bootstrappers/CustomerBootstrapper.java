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
//@Profile("bootstrap")
public class CustomerBootstrapper implements CommandLineRunner {

    @Autowired
    private CustomerRepository cRepo;
    @Autowired
    private VehicleRepository vRepo;

    @Override
    public void run(String... args) {
        values(502113846L, "Emily Clarke", List.of("AR77TI"));
        values(508776392L, "Aiden Turner", List.of("45MR02"));
        values(510229874L, "Nora Sanchez", List.of("55ND41", "28XQ51"));
        values(507332145L, "Marcus Yamamoto", List.of("05MJ38"));
        values(513884267L, "Isabella Rossi", List.of("71NM25"));
        values(501776508L, "Leo Kim", List.of("76NX08", "97XZ99"));
        values(515660392L, "Sofia Patel", List.of("04QB57", "37UP37"));
        values(512223876L, "Oliver Johnson", List.of("93TV30", "AV09VT"));
        values(506779330L, "Maya Gupta", List.of("19SJ63", "AH43MR", "AQ00UX"));
        values(514884112L, "Rui Soares", List.of("46ON05", "32VT07", "AG20JZ", "57ZE77"));
    }

    private void createSampleVehicle(Long customerID, List<String> licensePlates) {
        List<Vehicle> vehicleList = new ArrayList<>();

        for (String licensePlate: licensePlates) {
            vehicleList.add(Vehicle.builder().customerID(customerID).plateNumber(licensePlate).vehicleType(VehicleType.AUTOMOBILE).vehicleEnergySource(VehicleEnergySource.FUEL).build());
        }

        vRepo.saveAll(vehicleList);
    }

    private void values(Long customerNif, String customerName, List<String> licensePlates) {
        if (cRepo.findByNif(customerNif) == null) {
            Customer customer = Customer.builder().nif(customerNif).name(customerName).status(Status.ACTIVE).build();
            cRepo.save(customer);
            createSampleVehicle(customer.getCustomerID(), licensePlates);
        }
    }
}
