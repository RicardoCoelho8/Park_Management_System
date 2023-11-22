package labdsoft.park_bo_mcs.dtos.park;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarrierDisplayDTO {
    private Long barrierID;
    private Long parkID;
    private Boolean success;
    private String message;
}
