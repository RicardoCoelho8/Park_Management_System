package labdsoft.payments_bo_mcs.repositories;

import labdsoft.payments_bo_mcs.model.Payments;
import labdsoft.payments_bo_mcs.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Payments p WHERE p.userID=:userID")
    void deleteBySku(@Param("userID") Long userID);

    @Transactional
    @Modifying
    @Query("UPDATE Payments p SET p.userID = :sku WHERE p.userID=:userID")
    Payments updateBySku(@Param("userID") String userID);

    @Query("SELECT p FROM User p WHERE p.nif=:nif")
    Optional<User> findByNif(Integer nif);

}

