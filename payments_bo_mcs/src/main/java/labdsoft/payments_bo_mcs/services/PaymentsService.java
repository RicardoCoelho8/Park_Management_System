package labdsoft.payments_bo_mcs.services;


import labdsoft.payments_bo_mcs.model.barrier.BarrierInfoDTO;
import labdsoft.payments_bo_mcs.model.payment.PaymentsDTO;

import java.util.Date;

public interface PaymentsService {

    PaymentsDTO createFromBarrier(final BarrierInfoDTO barrierInfoDTO) throws Exception;

    Iterable<PaymentsDTO> findByUserNIF(final String nif);

    Iterable<PaymentsDTO> getCatalog();
}
