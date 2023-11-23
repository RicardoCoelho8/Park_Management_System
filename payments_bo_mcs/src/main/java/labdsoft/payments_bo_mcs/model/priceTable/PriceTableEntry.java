package labdsoft.payments_bo_mcs.model.priceTable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private Long parkId;
    private String periodStart;
    private String periodEnd;
    @ElementCollection
    @CollectionTable(name = "price_table_thresholds")
    private List<ThresholdCost> thresholds;
}