package labdsoft.user_bo_mcs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import labdsoft.user_bo_mcs.model.Email;
import labdsoft.user_bo_mcs.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT p FROM User p WHERE p.email=:email")
    Optional<User> getUserByEmail(Email email);
}

