package labdsoft.barrier_mcs_fe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarrierDTO {
    private Long barrierID;
    private Long parkID;
    private Boolean success;
    private String message;
}