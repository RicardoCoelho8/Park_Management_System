package labdsoft.user_bo_mcs.repositories;

import labdsoft.user_bo_mcs.model.Email;
import labdsoft.user_bo_mcs.model.Role;
import labdsoft.user_bo_mcs.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT p FROM User p WHERE p.email=:email")
    Optional<User> getUserByEmail(Email email);

    @Query("SELECT p FROM User p WHERE p.userId=:userId")
    Optional<User> findByUserId(Long userId);

    List<User> findAllByRole(Role role);
}

