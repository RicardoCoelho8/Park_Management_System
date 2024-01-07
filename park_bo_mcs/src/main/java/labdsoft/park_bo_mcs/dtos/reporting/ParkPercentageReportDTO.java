package labdsoft.park_bo_mcs.dtos.reporting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkPercentageReportDTO {
    private Double percentageCar;
    private Double percentageMotorcycle;
    private Double percentageFuel;
    private Double percentageGPL;
    private Double percentageElectric;
    private Double totalVehicles;
}
