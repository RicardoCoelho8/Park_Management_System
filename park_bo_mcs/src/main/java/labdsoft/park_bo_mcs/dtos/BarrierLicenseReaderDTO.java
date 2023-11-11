package labdsoft.park_bo_mcs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarrierLicenseReaderDTO {
    private Long barrierID;
    private String plateNumber;
    private Long parkNumber;
    private Date date;
}
