package labdsoft.user_bo_mcs.services;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import labdsoft.user_bo_mcs.model.*;


public interface UserService {
    UserDTO create(final UserOnCreation manager) throws Exception;
    Optional<AccessToken> login(final UserCredentials credentials) throws Exception;

    List<UserDTO> getAll();
    VehicleOnCreation addVehicle(Long userId, VehicleOnCreation vehicle) throws Exception;
    PaymentDTO changePaymentMethod(Long userId, PaymentRequest pMethod) throws Exception;

    Set<VehicleOnCreation> getAllUserVehicles(Long userId) throws Exception;
    PaymentDTO getUserPaymentMethod(Long userId) throws Exception;

}
