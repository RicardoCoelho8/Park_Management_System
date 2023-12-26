package labdsoft.park_bo_mcs.dtos.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkTimeReportDTO {
    private Long customerId;
    private Double timePeriod;
    private Integer year;
    private Integer month;
    private Integer day;
}
