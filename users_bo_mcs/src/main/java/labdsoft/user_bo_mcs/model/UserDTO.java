package labdsoft.user_bo_mcs.model;


import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    private String firstName;

    private String lastName;

    private String email;


    private int nif;

    private Set<ParkingHistory> parkingHistory;

    private int totalParkies;

    private Set<Vehicle> vehicles;

    private Role role;

    private PaymentMethod paymentMethod;
    
    private UserStatus userStatus;

}
