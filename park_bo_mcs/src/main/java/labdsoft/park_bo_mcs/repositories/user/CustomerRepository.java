package labdsoft.park_bo_mcs.repositories.user;

import labdsoft.park_bo_mcs.models.user.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByCustomerID(final Long customerID);

    Customer findByNif(final Long nif);

}
