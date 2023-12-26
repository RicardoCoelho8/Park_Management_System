package labdsoft.park_bo_mcs.services;

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
    public List<ParkReportDTO> getParkingReport(String vehicleType, String fuelType, Long parkId, String timePeriod) {
        List<ParkReportDTO> parkReportDTO = new ArrayList<>();

        List<ParkReport> parkReports = parkReportRepository.findAllByParkId(parkId);

        for (ParkReport parkReport : parkReports) {
            switch (vehicleType) {
                case "car":
                    switch (fuelType) {
                        case "fuel":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalCarsFuel() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalCarsFuel())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "electric":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalCarsElectrics() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalCarsElectrics())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "gpl":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalCarsGPL() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalCarsGPL())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "all":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalCars() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalCars())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        default:
                            return null;
                    }
                    break;
                case "motorcycle":
                    switch (fuelType) {
                        case "fuel":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalMotorcyclesFuel() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalMotorcyclesFuel())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "electric":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalMotorcyclesElectrics() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalMotorcyclesElectrics())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "gpl":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalMotorcyclesGPL() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalMotorcyclesGPL())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "all":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalMotorcycles() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalMotorcycles())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        default:
                            return null;
                    }
                    break;
                case "vehicle":
                    switch (fuelType) {
                        case "fuel":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalFuel() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalFuel())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "electric":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalElectrics() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalElectrics())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());

                            break;
                        case "gpl":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(parkReport.getTotalGPL() / parkReport.getTotalVehicles() * 100)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalGPL())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());
                            break;
                        case "all":
                            parkReportDTO.add(ParkReportDTO.builder()
                                    .percentage(100.0)
                                    .totalVehicles(parkReport.getTotalVehicles())
                                    .totalRequested(parkReport.getTotalVehicles())
                                    .year(parkReport.getYear())
                                    .month(parkReport.getMonth())
                                    .day(parkReport.getDay())
                                    .build());
                            break;
                        default:
                            return null;
                    }
                    break;
                default:
                    return null;
            }
        }

        Double totalVehiclesAux = 0.0;
        Double totalRequestedAux = 0.0;

        switch (timePeriod) {
            case "day":
                return parkReportDTO;
            case "month":
                List<ParkReportDTO> parkReportDTOMonth = new ArrayList<>();

                for (ParkReportDTO reportDTO : parkReportDTO) {
                    List<ParkReportDTO> parkReportDTOAux = parkReportDTO.stream().filter(parkReportDTO1 ->
                            parkReportDTO1.getMonth().equals(reportDTO.getMonth()) &&
                                    parkReportDTO1.getYear().equals(reportDTO.getYear())).toList();

                    for (ParkReportDTO reportDTO1 : parkReportDTOAux) {
                        parkReportDTO.remove(reportDTO1);

                        totalVehiclesAux += reportDTO1.getTotalVehicles();
                        totalRequestedAux += reportDTO1.getTotalRequested();
                    }

                    parkReportDTOMonth.add(ParkReportDTO.builder()
                            .percentage(totalRequestedAux / totalVehiclesAux * 100)
                            .totalVehicles(totalVehiclesAux)
                            .totalRequested(totalRequestedAux)
                            .year(reportDTO.getYear())
                            .month(reportDTO.getMonth())
                            .day(0)
                            .build());

                    totalVehiclesAux = 0.0;
                    totalRequestedAux = 0.0;
                }

                return parkReportDTOMonth;
            case "year":
                List<ParkReportDTO> parkReportDTOYear = new ArrayList<>();

                for (ParkReportDTO reportDTO : parkReportDTO) {
                    List<ParkReportDTO> parkReportDTOAux = parkReportDTO.stream().filter(parkReportDTO1 ->
                            parkReportDTO1.getYear().equals(reportDTO.getYear())).toList();

                    for (ParkReportDTO reportDTO1 : parkReportDTOAux) {
                        parkReportDTO.remove(reportDTO1);

                        totalVehiclesAux += reportDTO1.getTotalVehicles();
                        totalRequestedAux += reportDTO1.getTotalRequested();
                    }

                    parkReportDTOYear.add(ParkReportDTO.builder()
                            .percentage(totalRequestedAux / totalVehiclesAux * 100)
                            .totalVehicles(totalVehiclesAux)
                            .totalRequested(totalRequestedAux)
                            .year(reportDTO.getYear())
                            .month(0)
                            .day(0)
                            .build());

                    totalVehiclesAux = 0.0;
                    totalRequestedAux = 0.0;
                }

                return parkReportDTOYear;
            default:
                return null;
        }
    }
}
