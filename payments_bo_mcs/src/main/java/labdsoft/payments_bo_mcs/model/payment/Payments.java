package labdsoft.payments_bo_mcs.model.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long invoice;

    @Column(nullable = true, unique = false)
    private Double discount;

    @Column(nullable = false)
    @OneToMany
    private List<PaymentsTableRow> paymentsTableRows;

    @Column(nullable = false)
    private Long nif;

    public PaymentsDTO toDTO() {
        BigDecimal bigDecimalValue = new BigDecimal(paymentsTableRows.stream().mapToDouble(PaymentsTableRow::getPrice).sum() - discount);
        BigDecimal roundedValue = bigDecimalValue.setScale(2, RoundingMode.HALF_UP);

        ArrayList<PaymentsTableRowDTO> paymentsTableRowsDTO = paymentsTableRows.stream().map(PaymentsTableRow::toDTO).collect(Collectors.toCollection(ArrayList::new));
        return new PaymentsDTO(invoice, discount, roundedValue.doubleValue(), paymentsTableRowsDTO, nif);
    }
}
