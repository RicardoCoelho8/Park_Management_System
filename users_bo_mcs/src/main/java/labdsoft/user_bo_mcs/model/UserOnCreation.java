package labdsoft.user_bo_mcs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOnCreation {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String accountNumber;

    private int nif;

}
