package labdsoft.park_bo_mcs.dtos.park;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarrierLicenseReaderDTO {
    private Long barrierID;
    private Long parkID;
    private Long parkNumber;
    private String plateNumber;
    private Calendar date;

    public BarrierDisplayDTO toBarrierDisplayDTO(BarrierLicenseReaderDTO barrierLicenseReaderDTO) {
        return BarrierDisplayDTO.builder()
                .barrierID(barrierLicenseReaderDTO.getBarrierID())
                .parkID(barrierLicenseReaderDTO.getParkID())
                .success(false)
                .build();
    }
}
