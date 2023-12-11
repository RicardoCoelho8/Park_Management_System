package labdsoft.park_bo_mcs.dtos.park;

import labdsoft.park_bo_mcs.models.park.SpotType;
import labdsoft.park_bo_mcs.models.park.SpotVehicleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpotDTO {
    private String spotNumber;
    private SpotType spotType;
    private SpotVehicleType spotVehicleType;
    private String floorLevel;
    private boolean occupied;
    private Boolean operational;
}
