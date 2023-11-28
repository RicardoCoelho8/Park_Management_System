package labdsoft.park_bo_mcs.models.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleDTO {

    private String licensePlateNumber;

    private VehicleType vehicleType;

    private VehicleEnergySource vehicleEnergySource;

}
