package labdsoft.park_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.park_bo_mcs.dtos.reporting.ParkPercentageReportDTO;
import labdsoft.park_bo_mcs.dtos.reporting.ParkTimeReportDTO;
import labdsoft.park_bo_mcs.services.ParkReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ParkReport", description = "Endpoints for reporting parks")
@RestController
@RequestMapping("/parkReport")
public class ParkReportController {

    @Autowired
    private ParkReportService service;

    @Operation(summary = "Get all parking time of customers by park id and time period")
    @GetMapping("/parkingTime/{parkId}/{timePeriod}")
    public ResponseEntity<List<ParkTimeReportDTO>> getParkingTimeOfCustomers(@PathVariable Long parkId, @PathVariable String timePeriod) {
        return new ResponseEntity<>(service.getParkingTimeOfCustomers(parkId, timePeriod), HttpStatus.OK);
    }

    @Operation(summary = "Get all parking report by vehicle type, fuel type, park id and time period")
    @GetMapping("/percentageOf/{parkId}")
    public ResponseEntity<ParkPercentageReportDTO> getParkingReport(@PathVariable Long parkId) {
        return new ResponseEntity<>(service.getParkingReport(parkId), HttpStatus.OK);
    }
}
