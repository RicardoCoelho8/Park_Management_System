package labdsoft.user_bo_mcs.model;


import lombok.*;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleOnCreation {

    private String licensePlateNumber;

    private VehicleType vehicleType;

    private VehicleEnergySource vehicleEnergySource;
}
