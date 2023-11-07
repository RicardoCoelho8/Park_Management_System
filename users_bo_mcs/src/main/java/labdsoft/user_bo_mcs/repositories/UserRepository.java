package labdsoft.user_bo_mcs.repositories;

import org.springframework.data.repository.CrudRepository;

import labdsoft.user_bo_mcs.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}

