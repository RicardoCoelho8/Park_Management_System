package labdsoft.payments_bo_mcs.model.user;

import jakarta.persistence.*;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long userID;

    @Column(nullable = false, unique = true)
    private Long nif;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    @Column(nullable = false)
    private int totalParkies;

}
