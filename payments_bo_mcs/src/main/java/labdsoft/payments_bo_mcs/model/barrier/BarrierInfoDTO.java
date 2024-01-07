package labdsoft.payments_bo_mcs.model.barrier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BarrierInfoDTO {
    private Calendar enterPark;
    private Calendar leftPark;
    private Long parkID;
    private String licensePlateNumber;

    private boolean useParky;
}
