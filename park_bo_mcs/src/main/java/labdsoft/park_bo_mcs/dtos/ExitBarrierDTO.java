package labdsoft.park_bo_mcs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExitBarrierDTO {
    private Long barrierID;
    private String barrierNumber;
    private Long parkid;
    private Boolean success;
}
