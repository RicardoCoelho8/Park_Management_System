package labdsoft.user_bo_mcs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Top10ParkyDTO {
    private String name;
    private String email;
    private Integer parkies;
}
