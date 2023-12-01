package labdsoft.payments_bo_mcs.model.user;

import labdsoft.payments_bo_mcs.model.park.ParkingHistory;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

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
