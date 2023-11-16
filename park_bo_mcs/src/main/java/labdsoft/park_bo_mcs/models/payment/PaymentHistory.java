package labdsoft.park_bo_mcs.models.payment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long paymentHistoryId;

    @Column(nullable = false)
    private Float price;

    @Column
    private Boolean paid;

    @Column
    private Long customerID;
}
