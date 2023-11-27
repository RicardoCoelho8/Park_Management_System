package labdsoft.park_bo_mcs.dtos.park;


import labdsoft.park_bo_mcs.models.park.SpotType;
import labdsoft.park_bo_mcs.models.park.SpotVehicleType;
import labdsoft.park_bo_mcs.models.user.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotTypeOccupancyDTO {
    private SpotVehicleType spotVehicleType;
    private SpotType spotType;
    private int availableSpots;
}
