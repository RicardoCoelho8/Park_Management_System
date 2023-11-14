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

    @Query("SELECT p FROM Payments p WHERE p.invoice=:invoice")
    Optional<Payments> findById(Long invoice);

    @Query("SELECT p FROM Payments p")
    Optional<List<Payments>> getAllPayments();
}

