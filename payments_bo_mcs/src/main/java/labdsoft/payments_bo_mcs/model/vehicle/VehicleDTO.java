package labdsoft.payments_bo_mcs.model.vehicle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleDTO {

    private String licensePlateNumber;

    private VehicleType vehicleType;

    private VehicleEnergySource vehicleEnergySource;

}
