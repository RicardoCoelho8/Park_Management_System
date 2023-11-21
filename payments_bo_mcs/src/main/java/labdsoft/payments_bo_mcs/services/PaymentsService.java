package labdsoft.payments_bo_mcs.services;


import labdsoft.payments_bo_mcs.model.payment.PaymentsDTO;

import java.util.Date;

public interface PaymentsService {

    PaymentsDTO createFromBarrier(final Date enterParkDate, final Date leftParkDate, final Long parkID, final String licensePlateNumber) throws Exception;

    Iterable<PaymentsDTO> findByUserNIF(final String nif);

    Iterable<PaymentsDTO> getCatalog();
}
