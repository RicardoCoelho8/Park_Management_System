package labdsoft.park_bo_mcs.dtos;


import labdsoft.park_bo_mcs.models.park.SpotType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotTypeOccupancyDTO {
    private SpotType spotType;
    private int availableSpots;
}
