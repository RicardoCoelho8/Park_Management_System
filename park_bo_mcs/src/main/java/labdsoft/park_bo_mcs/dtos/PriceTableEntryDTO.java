package labdsoft.park_bo_mcs.dtos;

import labdsoft.park_bo_mcs.models.park.ThresholdCost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceTableEntryDTO {
    private Long parkId;
    private String periodStart;
    private String periodEnd;
    private List<ThresholdCost> thresholds;
}
