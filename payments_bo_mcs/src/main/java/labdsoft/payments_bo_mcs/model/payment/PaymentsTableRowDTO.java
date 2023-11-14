package labdsoft.payments_bo_mcs.model.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsTableRowDTO {
    private Long paymentTableRowID;
    private String periodStart;
    private String periodEnd;
    private String fractionStart;
    private String fractionEnd;
    private String vehicleType;
    private Double price;

    public PaymentsTableRow toObject() {
        return new PaymentsTableRow(paymentTableRowID, periodStart, periodEnd, fractionStart, fractionEnd, vehicleType, price);
    }
}