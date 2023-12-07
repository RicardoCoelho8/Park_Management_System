package labdsoft.park_bo_mcs.dtos.park;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SpotHistoryDTO {
    private Long parkNumber;
    private String spotNumber;
    private Boolean operational;
    private String managerName;
}
