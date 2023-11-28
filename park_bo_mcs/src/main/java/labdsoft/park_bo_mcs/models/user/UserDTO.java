package labdsoft.park_bo_mcs.models.user;

import java.util.Set;

import labdsoft.park_bo_mcs.models.park.ParkingHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private int nif;

    private Set<ParkingHistory> parkingHistory;

    private int totalParkies;

    private Set<VehicleDTO> vehicles;

    private String role;

    private String paymentMethod;

    private Status userStatus;

}
