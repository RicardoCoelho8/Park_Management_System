package labdsoft.user_bo_mcs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDTO {
    private PaymentMethod paymentMethod;
}
