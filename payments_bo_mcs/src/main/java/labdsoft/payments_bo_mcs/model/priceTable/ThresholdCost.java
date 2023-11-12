package labdsoft.payments_bo_mcs.model.priceTable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThresholdCost {
    private int thresholdMinutes;
    private double costPerMinuteAutomobiles;
    private double costPerMinuteMotorcycles;
}
