package labdsoft.park_bo_mcs.model;

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
public class Barrier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long barrierID;

    @Column(nullable = false)
    private String barrierNumber;

    @Column(nullable = false)
    private State state;

    @Column(nullable = false)
    private Long parkid;
}
