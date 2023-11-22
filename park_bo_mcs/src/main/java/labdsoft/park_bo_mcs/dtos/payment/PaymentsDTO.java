package labdsoft.park_bo_mcs.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsDTO {
    private Long invoice;
    private Double discount;
    private Double finalPrice;
    private ArrayList<PaymentsTableRowDTO> paymentsTableRowsDTO;
    private Long nif;
}
