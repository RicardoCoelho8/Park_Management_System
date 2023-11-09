package labdsoft.payments_bo_mcs.services;


import labdsoft.payments_bo_mcs.model.Payments;
import labdsoft.payments_bo_mcs.model.PaymentsDTO;

import java.util.List;
import java.util.Optional;

public interface PaymentsService {
    PaymentsDTO create(final PaymentsDTO paymentsDTO) throws Exception;

    Iterable<PaymentsDTO> findByUserNIF(final String nif);

}
