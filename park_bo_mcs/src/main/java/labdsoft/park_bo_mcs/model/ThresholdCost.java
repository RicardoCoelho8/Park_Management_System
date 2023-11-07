package labdsoft.park_bo_mcs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThresholdCost {
    @Column(nullable = false)
    private int thresholdMinutes;

    @Column(nullable = false)
    private double costPerMinuteAutomobiles;

    @Column(nullable = false)
    private double costPerMinuteMotorcycles;
}
