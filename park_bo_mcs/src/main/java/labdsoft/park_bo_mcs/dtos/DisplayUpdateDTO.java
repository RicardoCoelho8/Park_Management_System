package labdsoft.park_bo_mcs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisplayUpdateDTO {
    private Long barrierID;
    private Long parkID;
    private Long parkNumber;
    private String message;

    public BarrierDisplayDTO toBarrierDisplayDTO(DisplayUpdateDTO barrierLicenseReaderDTO) {
        return BarrierDisplayDTO.builder()
                .barrierID(barrierLicenseReaderDTO.getBarrierID())
                .parkID(barrierLicenseReaderDTO.getParkID())
                .success(true)
                .message(barrierLicenseReaderDTO.getMessage())
                .build();
    }

    public BarrierDisplayDTO toBarrierDisplayDTOFailure(DisplayUpdateDTO barrierLicenseReaderDTO, String message) {
        return BarrierDisplayDTO.builder()
                .barrierID(barrierLicenseReaderDTO.getBarrierID())
                .parkID(barrierLicenseReaderDTO.getParkID())
                .success(true)
                .message(message)
                .build();
    }
}
