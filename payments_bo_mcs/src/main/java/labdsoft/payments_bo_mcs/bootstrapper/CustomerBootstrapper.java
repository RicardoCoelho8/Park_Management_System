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
        values(502113846L, List.of("AR77TI"));
        values(508776392L, List.of("45MR02"));
        values(510229874L, List.of("55ND41", "28XQ51"));
        values(507332145L, List.of("05MJ38"));
        values(513884267L, List.of("71NM25"));
        values(501776508L, List.of("76NX08", "97XZ99"));
        values(515660392L, List.of("04QB57", "37UP37"));
        values(512223876L, List.of("93TV30", "AV09VT"));
        values(506779330L, List.of("19SJ63", "AH43MR", "AQ00UX"));
        values(514884112L, List.of("46ON05", "32VT07", "AG20JZ", "57ZE77"));
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
            AppUser customer = AppUser.builder().nif(customerNif).vehicles(createSampleVehicle(licensePlates)).build();
            uRepo.save(customer);
        }
    }
}
