package labdsoft.payments_bo_mcs.model.park;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long parkingHistoryId;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTime;

    @Column
    private Integer hoursBetweenEntranceExit;

    @Column
    private Integer minutesBetweenEntranceExit;

    @Column(nullable = false)
    private Long parkId;

    @Column(nullable = false)
    private Long customerID;

    @Column(nullable = false)
    private Double price;
}
