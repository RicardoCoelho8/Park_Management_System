package labdsoft.payments_bo_mcs.model.user;

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
    private int nif;
    private Set<VehicleDTO> vehicles;
}
