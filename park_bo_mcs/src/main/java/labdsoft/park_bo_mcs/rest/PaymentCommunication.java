package labdsoft.park_bo_mcs.rest;

import labdsoft.park_bo_mcs.dtos.park.SendToPaymentDTO;
import labdsoft.park_bo_mcs.dtos.payment.PaymentsDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class PaymentCommunication {

    @Autowired
    private final RestTemplate restTemplate;

    private final String URL = "lb://park20-payments-microservice/payments";

    public PaymentsDTO postForPayment(SendToPaymentDTO sendToPaymentDTO) {
        ResponseEntity<PaymentsDTO> response = this.restTemplate
                .postForEntity(URL, sendToPaymentDTO, PaymentsDTO.class);

        return response.getBody();
    }
}
