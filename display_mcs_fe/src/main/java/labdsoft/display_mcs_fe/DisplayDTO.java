package labdsoft.display_mcs_fe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisplayDTO{
    private String message;
    private Integer occupancy;
}