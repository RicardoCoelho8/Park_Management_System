package labdsoft.park_bo_mcs.dtos.park;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OvernightConfigDTO {
    private double overnightFee;
    private boolean enabled;
}
