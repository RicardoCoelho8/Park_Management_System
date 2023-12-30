package labdsoft.park_bo_mcs.dtos.park;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearbyParkOccupancyDTO {
    private Long parkId;
    private double distanceKm;
    private List<SpotTypeOccupancyDTO> spotTypeOccupancies;
    private double latitude;
    private double longitude;
}
