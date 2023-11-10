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
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long spotID;

    @Column(nullable = false)
    private String spotNumber;

    @Column(nullable = false)
    private int spotType;

    @Column(nullable = false)
    private String floorLevel;

    @Column(nullable = false)
    private boolean occupied;

    @Column(nullable = false)
    private boolean operational;

    @Column(nullable = false)
    private Long parkID;
}
