package labdsoft.payments_bo_mcs.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsTableRowDTO {
    private Date periodStart;
    private Date periodEnd;
    private Date fractionStart;
    private Date fractionEnd;
    private String vehicleType;
    private Double price;

    public PaymentsTableRow toObject() {
        return new PaymentsTableRow(1,periodStart,periodEnd,fractionStart,fractionEnd,vehicleType,price);
    }
}
