package labdsoft.user_bo_mcs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserOnCreation {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String accountNumber;

    private int nif;
    
    private String licensePlateNumber;

    private VehicleType vehicleType;

    private PaymentMethod paymentMethod;

    


}
