package labdsoft.user_bo_mcs.services;


import java.util.List;

import labdsoft.user_bo_mcs.model.UserDTO;
import labdsoft.user_bo_mcs.model.UserOnCreation;


public interface UserService {
    UserDTO create(final UserOnCreation manager) throws Exception;
    List<UserDTO> getAll();

}
