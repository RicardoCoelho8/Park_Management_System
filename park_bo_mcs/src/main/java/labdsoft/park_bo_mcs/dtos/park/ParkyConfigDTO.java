package labdsoft.park_bo_mcs.dtos.park;

import jakarta.persistence.Column;
import labdsoft.park_bo_mcs.models.park.SpotType;
import labdsoft.park_bo_mcs.models.park.SpotVehicleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkyConfigDTO {
    private int parkiesPerHour;
    private int parkiesPerMinute;
}
