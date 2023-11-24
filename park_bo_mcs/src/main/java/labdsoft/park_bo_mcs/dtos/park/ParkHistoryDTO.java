package labdsoft.park_bo_mcs.dtos.park;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkHistoryDTO {
    private Long parkingHistoryId;
    private Calendar startTime;
    private Calendar endTime;
    private Long parkId;
    private Long customerID;
}
