package labdsoft.park_bo_mcs.dtos;

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
    private Long parkNumber;
    private int occupancy;
    private int currentCapacity;
    private double distanceKm;
}