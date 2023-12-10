package labdsoft.user_bo_mcs.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Getter
public class ParkyTransactionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    @Column
    private Integer amount;

    @Column
    private String reason;

    // manager who made the transaction, can be null if invoice payment for example
    @Column(nullable = true)
    private Long managerId;

    @Column
    private LocalDateTime transactionTime;

    public ParkyTransactionEvent(Integer amount, String reason, LocalDateTime transactionTime, Long managerId) {
        this.amount = amount;
        this.reason = reason;
        this.transactionTime = transactionTime;
        this.managerId = managerId;
    }

}
