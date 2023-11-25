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
public class SendToPaymentDTO {
    private Calendar enterPark;
    private Calendar leftPark;
    private Long parkID;
    private String licensePlateNumber;
}
