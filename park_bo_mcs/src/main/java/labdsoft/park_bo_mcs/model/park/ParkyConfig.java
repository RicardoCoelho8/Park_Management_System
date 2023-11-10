package labdsoft.park_bo_mcs.model.park;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkyConfig {
    @Column(nullable = false)
    private int parkiesPerHour;

    @Column(nullable = false)
    private int parkiesPerMinute;
}
