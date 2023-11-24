package labdsoft.user_bo_mcs.model;

import lombok.*;

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

    private VehicleEnergySource vehicleEnergySource;

    private PaymentMethod paymentMethod;

    


}
