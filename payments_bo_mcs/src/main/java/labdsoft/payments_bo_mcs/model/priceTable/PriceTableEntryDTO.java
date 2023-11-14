package labdsoft.payments_bo_mcs.model.priceTable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceTableEntryDTO {
    private Long parkId;
    private String periodStart;
    private String periodEnd;
    private ArrayList<ThresholdCost> thresholds;
}
