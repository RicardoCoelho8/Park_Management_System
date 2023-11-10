package labdsoft.park_bo_mcs.model.park;

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
public class Park {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long parkID;

    @Column(nullable = false)
    private Long parkNumber;

    @Column(nullable = false)
    private int maxOcuppancy;

    @Embedded
    @Column(nullable = false)
    private ParkyConfig parkyConfig;

    @Embedded
    @Column(nullable = false)
    private Location location;
}
