package labdsoft.payments_bo_mcs.repositories;

import labdsoft.payments_bo_mcs.model.payment.Payments;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<AppUser, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM AppUser p WHERE p.userID=:userID")
    void deleteBySku(@Param("userID") Long userID);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser p SET p.userID = :sku WHERE p.userID=:userID")
    Payments updateBySku(@Param("userID") String userID);

    @Query("SELECT p FROM AppUser p WHERE p.nif=:nif")
    Optional<AppUser> findByNif(Long nif);

}

