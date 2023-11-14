package labdsoft.payments_bo_mcs.services;


import labdsoft.payments_bo_mcs.model.payment.PaymentsDTO;

public interface PaymentsService {

    Iterable<PaymentsDTO> findByUserNIF(final String nif);

    Iterable<PaymentsDTO> getCatalog();
}
