package labdsoft.user_bo_mcs.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import labdsoft.user_bo_mcs.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}

