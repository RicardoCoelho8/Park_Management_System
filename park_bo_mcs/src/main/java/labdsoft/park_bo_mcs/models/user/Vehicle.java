package labdsoft.park_bo_mcs.models.user;

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
    private Long userID;

    @Column(nullable = false)
    private String plateNumber;

    @Column(nullable = false)
    private String vehicleType;
}
