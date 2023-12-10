package labdsoft.user_bo_mcs.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParkyTransactionRequest {

    private List<Long> userIds;

    private Integer amount;
    private String reason;

}
