package labdsoft.park_bo_mcs.models.reporting;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long parkReportId;

    @Column(nullable = false)
    private Long parkId;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer day;

    @Column
    private Double totalVehicles;

    @Column
    private Double totalFuel;

    @Column
    private Double totalElectrics;

    @Column
    private Double totalGPL;

    @Column
    private Double totalMotorcycles;

    @Column
    private Double totalCars;

    @Column
    private Double totalMotorcyclesFuel;

    @Column
    private Double totalMotorcyclesElectrics;

    @Column
    private Double totalMotorcyclesGPL;

    @Column
    private Double totalCarsFuel;

    @Column
    private Double totalCarsElectrics;

    @Column
    private Double totalCarsGPL;
}
