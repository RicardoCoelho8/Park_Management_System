package labdsoft.park_bo_mcs.models.park;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OvernightConfig {
    @Column(nullable = false)
    private Integer overnightFee;

    @Column(nullable = false)
    private boolean enabled;
}
