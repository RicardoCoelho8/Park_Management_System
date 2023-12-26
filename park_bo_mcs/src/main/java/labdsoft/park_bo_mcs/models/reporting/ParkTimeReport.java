package labdsoft.park_bo_mcs.models.reporting;

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
public class ParkTimeReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long parkTimeReportId;

    @Column(nullable = false)
    private Long parkReportId;

    @Column(nullable = false)
    private Long customerId;

    @Column
    private Double timePeriod;
}
