package labdsoft.park_bo_mcs.repositories.user;

import labdsoft.park_bo_mcs.models.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User getUserByUserID(Long userID);
}
