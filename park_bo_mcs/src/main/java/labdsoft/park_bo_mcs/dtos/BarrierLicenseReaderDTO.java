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
    private Long parkid;
    private Long parkNumber;
    private String plateNumber;
    private Date date;

    public BarrierDisplayDTO toBarrierDisplayDTO(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        return BarrierDisplayDTO.builder()
                .barrierID(barrierLicenseReaderDTO.getBarrierID())
                .parkid(barrierLicenseReaderDTO.getParkid())
                .success(false)
                .build();
    }
}
