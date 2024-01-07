package labdsoft.park_bo_mcs.dtos.park;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkyFlagDTO {
    private String customerID;
    private Boolean parkyFlag;
}
