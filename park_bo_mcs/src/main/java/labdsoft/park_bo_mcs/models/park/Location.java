package labdsoft.park_bo_mcs.models.park;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    @Column(nullable = false)
    @Min(-90)
    @Max(90)
    private double latitude;

    @Column(nullable = false)
    @Min(-180)
    @Max(180)
    private double longitude;
}
