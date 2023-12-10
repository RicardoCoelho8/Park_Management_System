package labdsoft.user_bo_mcs.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ParkyWalletDTO {

    private Long userId;

    private Integer parkies;

    private List<ParkyTransactionEvent> parkyEvents;

}
