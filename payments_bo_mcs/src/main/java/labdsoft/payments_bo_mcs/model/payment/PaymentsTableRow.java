package labdsoft.payments_bo_mcs.model.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentsTableRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentTableRowID;

    @Column(nullable = false, unique = false)
    private String periodStart;

    @Column(nullable = false, unique = false)
    private String periodEnd;

    @Column(nullable = false, unique = false)
    private String fractionStart;

    @Column(nullable = false, unique = false)
    private String fractionEnd;

    @Column(nullable = false, unique = false)
    private String vehicleType;

    @Column(nullable = false, unique = false)
    private Double price;

    public PaymentsTableRowDTO toDTO() {
        return new PaymentsTableRowDTO(paymentTableRowID, periodStart, periodEnd, fractionStart, fractionEnd, vehicleType, price);
    }
}
