package labdsoft.park_bo_mcs.repositories.user;

import labdsoft.park_bo_mcs.models.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User getUserByUserID(Long userID);
}
