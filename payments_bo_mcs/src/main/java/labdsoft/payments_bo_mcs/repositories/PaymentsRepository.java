package labdsoft.payments_bo_mcs.repositories;

import labdsoft.payments_bo_mcs.model.payment.Payments;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PaymentsRepository extends CrudRepository<Payments, Long> {

    @Query("SELECT p FROM Payments p WHERE p.nif=:nif")
    Optional<List<Payments>> getPaymentsOfUser(@Param("nif") String nif);

    @Transactional
    @Modifying
    @Query("DELETE FROM Payments p WHERE p.paymentID=:paymentID")
    void deleteByID(@Param("paymentID") Long paymentID);

    @Transactional
    @Modifying
    @Query("UPDATE Payments p SET p.paymentID = :sku WHERE p.paymentID=:paymentID")
    Payments updateByID(@Param("paymentID") Long paymentID);

    @Query("SELECT p FROM Payments p WHERE p.paymentID=:paymentID")
    Optional<Payments> findById(Long paymentID);

    @Query("SELECT p FROM Payments p")
    Optional<List<Payments>> getAllPayments();
}

