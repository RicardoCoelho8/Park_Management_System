package labdsoft.payments_bo_mcs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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
    private Date periodStart;

    @Column(nullable = false, unique = false)
    private Date periodEnd;

    @Column(nullable = false, unique = false)
    private Date fractionStart;

    @Column(nullable = false, unique = false)
    private Date fractionEnd;

    @Column(nullable = false, unique = false)
    private String vehicleType;

    @Column(nullable = false, unique = false)
    private Double price;

    public PaymentsTableRowDTO toDTO() {
        return new PaymentsTableRowDTO(periodStart, periodEnd, fractionStart, fractionEnd, vehicleType, price);
    }
}
