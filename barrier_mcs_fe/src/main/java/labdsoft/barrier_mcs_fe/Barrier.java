package labdsoft.barrier_mcs_fe;

import lombok.Builder;
import lombok.Data;

import java.util.Calendar;

@Data
@Builder
public class Barrier {
    private Long barrierID;
    private Long parkID;
    private Long parkNumber;
    private String plateNumber;
    private Calendar date;
}
