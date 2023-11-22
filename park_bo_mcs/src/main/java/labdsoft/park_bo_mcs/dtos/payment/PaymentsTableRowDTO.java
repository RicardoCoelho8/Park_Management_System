package labdsoft.park_bo_mcs.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
