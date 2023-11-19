package labdsoft.user_bo_mcs.services;


import java.util.List;
import java.util.Optional;

import labdsoft.user_bo_mcs.model.AccessToken;
import labdsoft.user_bo_mcs.model.PaymentMethod;
import labdsoft.user_bo_mcs.model.UserCredentials;
import labdsoft.user_bo_mcs.model.UserDTO;
import labdsoft.user_bo_mcs.model.UserOnCreation;
import labdsoft.user_bo_mcs.model.VehicleOnCreation;


public interface UserService {
    UserDTO create(final UserOnCreation manager) throws Exception;
    Optional<AccessToken> login(final UserCredentials credentials) throws Exception;

    List<UserDTO> getAll();
    UserDTO addVehicle(Long userId, VehicleOnCreation vehicle) throws Exception;
    UserDTO changePaymentMethod(Long userId, PaymentMethod pMethod) throws Exception;

}
