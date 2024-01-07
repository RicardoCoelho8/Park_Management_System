package labdsoft.park_bo_mcs.services;

import labdsoft.park_bo_mcs.dtos.reporting.ParkPercentageReportDTO;
import labdsoft.park_bo_mcs.dtos.reporting.ParkReportDTO;
import labdsoft.park_bo_mcs.dtos.reporting.ParkTimeReportDTO;
import labdsoft.park_bo_mcs.models.reporting.ParkReport;
import labdsoft.park_bo_mcs.models.reporting.ParkTimeReport;
import labdsoft.park_bo_mcs.repositories.reporting.ParkReportRepository;
import labdsoft.park_bo_mcs.repositories.reporting.ParkTimeReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkReportServiceImpl implements ParkReportService {

    @Autowired
    private ParkReportRepository parkReportRepository;

    @Autowired
    private ParkTimeReportRepository parkTimeReportRepository;

    @Override
    public List<ParkTimeReportDTO> getParkingTimeOfCustomers(Long parkId, String timePeriod) {
        List<ParkTimeReportDTO> parkTimeReportDTO = new ArrayList<>();

        List<ParkReport> parkReports = parkReportRepository.findAllByParkId(parkId);

        for (ParkReport parkReport : parkReports) {
            List<ParkTimeReport> parkTimeReport = parkTimeReportRepository.findAllByParkReportId(parkReport.getParkReportId());

            switch (timePeriod) {
                case "day":
                    for (ParkTimeReport timeReport : parkTimeReport) {
                        if (parkTimeReportDTO.stream().anyMatch(parkTimeReportDTO1 ->
                                parkTimeReportDTO1.getCustomerId().equals(timeReport.getCustomerId()) &&
                                        parkTimeReportDTO1.getDay().equals(parkReport.getDay()) &&
                                        parkTimeReportDTO1.getMonth().equals(parkReport.getMonth()) &&
                                        parkTimeReportDTO1.getYear().equals(parkReport.getYear()))) {
                            ParkTimeReportDTO parkTimeReportDTO1 = parkTimeReportDTO.stream().filter(parkTimeReportDTO2 ->
                                    parkTimeReportDTO2.getCustomerId().equals(timeReport.getCustomerId()) &&
                                            parkTimeReportDTO2.getDay().equals(parkReport.getDay()) &&
                                            parkTimeReportDTO2.getMonth().equals(parkReport.getMonth()) &&
                                            parkTimeReportDTO2.getYear().equals(parkReport.getYear())).findFirst().get();

                            parkTimeReportDTO.remove(parkTimeReportDTO1);

                            parkTimeReportDTO.add(ParkTimeReportDTO.builder()
                                    .customerId(timeReport.getCustomerId())
                                    .timePeriod(parkTimeReportDTO1.getTimePeriod() + timeReport.getTimePeriod())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());
                        } else {
                            parkTimeReportDTO.add(ParkTimeReportDTO.builder()
                                    .customerId(timeReport.getCustomerId())
                                    .timePeriod(timeReport.getTimePeriod())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());
                        }
                    }
                    break;
                case "month":
                    for (ParkTimeReport timeReport : parkTimeReport) {
                        if (parkTimeReportDTO.stream().anyMatch(parkTimeReportDTO1 ->
                                parkTimeReportDTO1.getCustomerId().equals(timeReport.getCustomerId()) &&
                                        parkTimeReportDTO1.getMonth().equals(parkReport.getMonth()) &&
                                        parkTimeReportDTO1.getYear().equals(parkReport.getYear()))) {
                            ParkTimeReportDTO parkTimeReportDTO1 = parkTimeReportDTO.stream().filter(parkTimeReportDTO2 ->
                                    parkTimeReportDTO2.getCustomerId().equals(timeReport.getCustomerId()) &&
                                            parkTimeReportDTO2.getMonth().equals(parkReport.getMonth()) &&
                                            parkTimeReportDTO2.getYear().equals(parkReport.getYear())).findFirst().get();

                            parkTimeReportDTO.remove(parkTimeReportDTO1);

                            parkTimeReportDTO.add(ParkTimeReportDTO.builder()
                                    .customerId(timeReport.getCustomerId())
                                    .timePeriod(parkTimeReportDTO1.getTimePeriod() + timeReport.getTimePeriod())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(0)
                                    .build());
                        } else {
                            parkTimeReportDTO.add(ParkTimeReportDTO.builder()
                                    .customerId(timeReport.getCustomerId())
                                    .timePeriod(timeReport.getTimePeriod())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(0)
                                    .build());
                        }
                    }
                    break;
                case "year":
                    for (ParkTimeReport timeReport : parkTimeReport) {
                        if (parkTimeReportDTO.stream().anyMatch(parkTimeReportDTO1 ->
                                parkTimeReportDTO1.getCustomerId().equals(timeReport.getCustomerId()) &&
                                        parkTimeReportDTO1.getYear().equals(parkReport.getYear()))) {
                            ParkTimeReportDTO parkTimeReportDTO1 = parkTimeReportDTO.stream().filter(parkTimeReportDTO2 ->
                                    parkTimeReportDTO2.getCustomerId().equals(timeReport.getCustomerId()) &&
                                            parkTimeReportDTO2.getYear().equals(parkReport.getYear())).findFirst().get();

                            parkTimeReportDTO.remove(parkTimeReportDTO1);

                            parkTimeReportDTO.add(ParkTimeReportDTO.builder()
                                    .customerId(timeReport.getCustomerId())
                                    .timePeriod(parkTimeReportDTO1.getTimePeriod() + timeReport.getTimePeriod())
                                    .year(parkReport.getYear())
                                    .month(0)
                                    .day(0)
                                    .build());
                        } else {
                            parkTimeReportDTO.add(ParkTimeReportDTO.builder()
                                    .customerId(timeReport.getCustomerId())
                                    .timePeriod(timeReport.getTimePeriod())
                                    .year(parkReport.getYear())
                                    .month(0)
                                    .day(0)
                                    .build());
                        }
                    }
                    break;
                default:
                    return null;
            }
        }

        return parkTimeReportDTO;
    }

    @Override
    public ParkPercentageReportDTO getParkingReport(Long parkId) {
        List<ParkReport> parkReports = parkReportRepository.findAllByParkId(parkId);

        double auxTotal = 0.0;
        double auxTotalCar = 0.0;
        double auxTotalMotor = 0.0;
        double auxTotalFuel = 0.0;
        double auxTotalGas = 0.0;
        double auxTotalElectric = 0.0;

        for (ParkReport parkReport : parkReports) {
            auxTotal = auxTotal + parkReport.getTotalVehicles();
            auxTotalCar = auxTotalCar + parkReport.getTotalCars();
            auxTotalMotor = auxTotalMotor + parkReport.getTotalMotorcycles();
            auxTotalFuel = auxTotalFuel + parkReport.getTotalFuel();
            auxTotalGas = auxTotalGas + parkReport.getTotalGPL();
            auxTotalElectric = auxTotalElectric + parkReport.getTotalElectrics();
        }

        if (auxTotal != 0.0) {
            return ParkPercentageReportDTO.builder().totalVehicles(auxTotal)
                    .percentageCar((auxTotalCar/auxTotal)*100)
                    .percentageMotorcycle((auxTotalMotor/auxTotal)*100)
                    .percentageFuel((auxTotalFuel/auxTotal)*100)
                    .percentageGPL((auxTotalGas/auxTotal)*100)
                    .percentageElectric((auxTotalElectric/auxTotal)*100)
                    .build();
        } else {
            return ParkPercentageReportDTO.builder().totalVehicles(auxTotal)
                    .percentageCar(0.0)
                    .percentageMotorcycle(0.0)
                    .percentageFuel(0.0)
                    .percentageGPL(0.0)
                    .percentageElectric(0.0)
                    .build();
        }
    }
}
