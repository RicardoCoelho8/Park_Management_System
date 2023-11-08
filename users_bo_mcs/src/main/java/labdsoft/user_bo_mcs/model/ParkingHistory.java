package labdsoft.user_bo_mcs.model;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ParkingHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long parkingHistoryId;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endTime;

    @Column
    private Long parkId;

    
}
