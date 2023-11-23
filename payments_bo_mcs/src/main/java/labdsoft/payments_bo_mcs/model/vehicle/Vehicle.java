package labdsoft.payments_bo_mcs.model.vehicle;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long vehicleID;

    @Column(nullable = false)
    private String plateNumber;

    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private VehicleEnergySource vehicleEnergySource;
}
