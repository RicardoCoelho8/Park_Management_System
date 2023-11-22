package labdsoft.user_bo_mcs.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleOnCreation {

    private String licensePlateNumber;

    private VehicleType vehicleType;

    private VehicleEnergySource vehicleEnergySource;

}
