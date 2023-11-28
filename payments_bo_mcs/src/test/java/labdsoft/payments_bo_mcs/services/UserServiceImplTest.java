package labdsoft.payments_bo_mcs.services;

import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.repositories.UserRepository;
import labdsoft.payments_bo_mcs.repositories.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private UserServiceImpl service;


    @Test
    void createFromSubscribe() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(Vehicle.builder().plateNumber("55-ND-41").build());

        when(vehicleRepository.saveAll(vehicles)).thenReturn(vehicles);

        AppUser toCreateFrom = AppUser.builder().nif(501776508L).vehicles(vehicles).build();

        AppUser appUser = AppUser.builder().userID(1L).nif(501776508L).vehicles((vehicles)).build();

        when(userRepository.save(toCreateFrom)).thenReturn(appUser);

        service.createFromSubscribe(toCreateFrom);
    }

    @Test
    void updateFromSubscribe() {

        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(Vehicle.builder().plateNumber("55-ND-41").build());
        vehicles.add(Vehicle.builder().plateNumber("AA-ND-41").build());

        AppUser toCreateFrom = AppUser.builder().nif(501776508L).vehicles(vehicles).build();

        AppUser appUser = AppUser.builder().userID(1L).nif(501776508L).vehicles(vehicles).build();

        when(userRepository.save(toCreateFrom)).thenReturn(appUser);

        service.updateFromSubscribe(toCreateFrom);
    }
}