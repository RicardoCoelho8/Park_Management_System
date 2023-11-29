package labdsoft.park_bo_mcs.dtos.park;

import jakarta.persistence.Column;
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
    private Integer hoursBetweenEntranceExit;
    private Integer minutesBetweenEntranceExit;
    private Long parkId;
    private Long customerID;
    private Double price;
}
