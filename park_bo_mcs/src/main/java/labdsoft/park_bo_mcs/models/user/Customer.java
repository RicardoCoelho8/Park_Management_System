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
public class Customer {
    @Id
    private Long customerID;

    @Column(nullable = false, unique = true)
    private Long nif;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Status status;
}
