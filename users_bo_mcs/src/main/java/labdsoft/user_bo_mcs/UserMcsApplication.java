package labdsoft.user_bo_mcs;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import labdsoft.user_bo_mcs.model.Email;
import labdsoft.user_bo_mcs.model.Name;
import labdsoft.user_bo_mcs.model.Password;
import labdsoft.user_bo_mcs.model.PaymentMethod;
import labdsoft.user_bo_mcs.model.Role;
import labdsoft.user_bo_mcs.model.TaxIdNumber;
import labdsoft.user_bo_mcs.model.User;
import labdsoft.user_bo_mcs.model.UserStatus;
import labdsoft.user_bo_mcs.model.Vehicle;
import labdsoft.user_bo_mcs.model.VehicleEnergySource;
import labdsoft.user_bo_mcs.model.VehicleType;
import labdsoft.user_bo_mcs.repositories.UserRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class UserMcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMcsApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner run(UserRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                String password = "123PasswordX#";
                String encodedPassword = this.passwordEncoder().encode(password);
                final User supervisor = new User(new Name("Paulo", "Gandra"), new Email("supervisor@isep.ipp.pt"),
                        new Password(encodedPassword), new TaxIdNumber(123123123), Role.SUPERVISOR,
                        new Vehicle("AA-20-CC", VehicleType.AUTOMOBILE, VehicleEnergySource.FUEL),
                        PaymentMethod.NOT_DEFINED, UserStatus.ENABLED);

                final User parkManager = new User(new Name("Park", "Manager"), new Email("parkmanager@isep.ipp.pt"),
                        new Password(encodedPassword), new TaxIdNumber(123123124), Role.PARK_MANAGER,
                        new Vehicle("BB-20-CC", VehicleType.AUTOMOBILE, VehicleEnergySource.FUEL), PaymentMethod.NOT_DEFINED, UserStatus.ENABLED);

                final User customerManager = new User(new Name("Customer", "Manager"),
                        new Email("customermanager@isep.ipp.pt"),
                        new Password(encodedPassword), new TaxIdNumber(123123125), Role.CUSTOMER_MANAGER,
                        new Vehicle("CC-20-CC", VehicleType.MOTORCYCLE, VehicleEnergySource.FUEL), PaymentMethod.NOT_DEFINED, UserStatus.ENABLED);

                final User customer = new User(new Name("Customer", "Customer"), new Email("customer@isep.ipp.pt"),
                        new Password(encodedPassword), new TaxIdNumber(123123126), Role.CUSTOMER,
                        new Vehicle("DD-20-CC", VehicleType.AUTOMOBILE, VehicleEnergySource.ELECTRIC), PaymentMethod.PAYPAL, UserStatus.ENABLED);

                repo.saveAll(Arrays.asList(supervisor, parkManager, customerManager, customer));

            } else {
                System.out.println("Database information already exists... skipping bootstrap");
            }

        };
    }

}
