package labdsoft.payments_bo_mcs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsDTO {
    private Double invoice;
    private Double discount;
    private Double finalPrice;
    private ArrayList<PaymentsTableRowDTO> paymentsTableRowsDTO;
    private Long nif;

    public Payments toObject() {
        ArrayList<PaymentsTableRow> paymentsTableRows = paymentsTableRowsDTO.parallelStream().map(PaymentsTableRowDTO::toObject).collect(Collectors.toCollection(ArrayList::new));
        return new Payments(1, invoice, discount, paymentsTableRows, nif);
    }
}
