package labdsoft.park_bo_mcs.dtos.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkReportDTO {
    private Double percentage;
    private Double totalVehicles;
    private Double totalRequested;
    private Integer year;
    private Integer month;
    private Integer day;
}
