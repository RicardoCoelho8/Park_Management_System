package labdsoft.park_bo_mcs.repositories.payment;

import labdsoft.park_bo_mcs.models.payment.PaymentHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentHistoryRepository extends CrudRepository<PaymentHistory, Long> {
    List<PaymentHistory> findAllByCustomerIDAndPaid(Long customerID, Boolean paid);
}
