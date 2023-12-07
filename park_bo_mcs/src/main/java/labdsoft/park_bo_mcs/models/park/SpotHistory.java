package labdsoft.park_bo_mcs.models.park;

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
public class SpotHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long spotHistoryID;

    @Column(nullable = false)
    private String spotNumber;

    @Column(nullable = false)
    private String HistoryOperational;
}
