package labdsoft.park_bo_mcs.models.park;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceTableEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entryId;

    @Column(nullable = false)
    private String period;

    @ElementCollection
    @CollectionTable(name = "price_table_thresholds")
    private List<ThresholdCost> thresholds;

    @Column(nullable = false)
    private Long parkId;
}