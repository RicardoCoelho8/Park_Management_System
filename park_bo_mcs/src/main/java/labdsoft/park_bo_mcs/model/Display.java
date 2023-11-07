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
public class Display {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long displayID;

    @Column(nullable = false)
    private String displayNumber;

    @Column(nullable = false)
    private State state;

    @Column(nullable = false)
    private Long parkid;
}
