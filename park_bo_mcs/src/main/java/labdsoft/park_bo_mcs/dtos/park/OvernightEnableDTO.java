package labdsoft.park_bo_mcs.dtos.park;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OvernightEnableDTO {
    private String parkNumber;
    private boolean status;
}
