package labdsoft.payments_bo_mcs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @Column(nullable = false, unique = false)
    private Double invoice;

    @Column(nullable = true, unique = false)
    private Double discount;

    @Column(nullable = false)
    @OneToMany
    private ArrayList<PaymentsTableRow> paymentsTableRows;

    @Column(nullable = false)
    private Long nif;

    public PaymentsDTO toDTO() {
        ArrayList<PaymentsTableRowDTO> paymentsTableRowsDTO = paymentsTableRows.parallelStream().map(PaymentsTableRow::toDTO).collect(Collectors.toCollection(ArrayList::new));
        return new PaymentsDTO(invoice, discount, paymentsTableRows.parallelStream().mapToDouble(PaymentsTableRow::getPrice).sum(), paymentsTableRowsDTO, nif);
    }
}
