package labdsoft.park_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.park_bo_mcs.communications.Subscribe;
import labdsoft.park_bo_mcs.dtos.park.*;
import labdsoft.park_bo_mcs.services.ParkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Barrier", description = "Endpoints for managing barriers")
@RestController
@RequestMapping("/parks")
class ParkController {
    private static final Logger logger = LoggerFactory.getLogger(ParkController.class);

    @Autowired
    private Subscribe subscribe;

    @Autowired
    private ParkService service;

    @Operation(summary = "Get all occupancies level from all parks")
    @GetMapping("/getAllOccupancies")
    public ResponseEntity<List<OccupancyParkDTO>> getCurrentNumberOfAvailableSpotsInsideAllParks() throws Exception {

        return new ResponseEntity<>(service.getCurrentNumberOfAvailableSpotsInsideAllParks(), HttpStatus.OK);
    }

    @Operation(summary = "Get all nearby parks occupancy and distance")
    @GetMapping("/real-time/occupancy/{userLatitude}/{userLongitude}/{maxDistanceKm}")
    public ResponseEntity<List<NearbyParkOccupancyDTO>> getRealTimeNearbyParksOccupancy(@PathVariable double userLatitude, @PathVariable double userLongitude, @PathVariable double maxDistanceKm) {
        logger.info("Received get all nearby parks occupancy and distance request");
        return new ResponseEntity<>(service.getRealTimeNearbyParksOccupancy(userLatitude, userLongitude, maxDistanceKm), HttpStatus.OK);
    }

    @Operation(summary = "Get all parking history by customer id")
    @GetMapping("/parkingHistory/{customerID}")
    public ResponseEntity<List<ParkHistoryDTO>> getAllParkingHistoryByCustomerID(@PathVariable Long customerID) {
        return new ResponseEntity<>(service.getAllParkingHistoryByCustomerID(customerID), HttpStatus.OK);
    }

    @Operation(summary = "Enable or disable parking spot by spotNumber")
    @PutMapping("/enableDisableSpot")
    public ResponseEntity<String> enableDisableSpot(@RequestBody SpotHistoryDTO spotHistoryDTO) {
        return new ResponseEntity<>(service.enableDisableSpot(spotHistoryDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get all parks")
    @GetMapping("/getAllParks")
    public ResponseEntity<List<String>> getAllParks() {
        return new ResponseEntity<>(service.getAllParks(), HttpStatus.OK);
    }

    @Operation(summary = "Get spots by park number")
    @GetMapping("/getSpotsByParkNumber/{parkNumber}")
    public ResponseEntity<List<SpotDTO>> getSpotsByParkNumber(@PathVariable String parkNumber) {
        return new ResponseEntity<>(service.getSpotsByParkNumber(parkNumber), HttpStatus.OK);
    }

    @Operation(summary = "Change Parky thresholds by park number")
    @PutMapping("/changeParkyThresholds/{parkNumber}")
    public ResponseEntity<String> changeParkyThresholds(@PathVariable String parkNumber, @RequestBody ParkyConfigDTO parkyConfigDTO) {
        return new ResponseEntity<>(service.changeParkyThresholds(parkNumber, parkyConfigDTO), HttpStatus.OK);
    }

    @Operation(summary = "Get Parky thresholds by park number")
    @GetMapping("/getParkyThresholds/{parkNumber}")
    public ResponseEntity<ParkyConfigDTO> getParkyThresholds(@PathVariable String parkNumber) {
        return new ResponseEntity<>(service.getParkyThresholds(parkNumber), HttpStatus.OK);
    }

    @Operation(summary = "Get Overnight config by park number")
    @GetMapping("/getOvernightConfig/{parkNumber}")
    public ResponseEntity<OvernightConfigDTO> getOvernightConfigByParkNumber(@PathVariable String parkNumber) {
        return new ResponseEntity<>(service.getOvernightConfigByParkNumber(parkNumber), HttpStatus.OK);
    }

    @Operation(summary = "Enable or disable overnight fee by park number")
    @PutMapping("/enableDisableOvernightFee")
    public ResponseEntity<String> enableDisableOvernightFeeByParkNumber(@RequestBody OvernightEnableDTO dto) {
        return new ResponseEntity<>(service.enableDisableOvernightFeeByParkNumber(dto), HttpStatus.OK);
    }

    @Operation(summary = "Change overnight fee price by park number")
    @PutMapping("/changeOvernightFeePrice")
    public ResponseEntity<String> changeOvernightFeePriceByParkNumber(@RequestBody OvernightPriceDTO dto) {
        return new ResponseEntity<>(service.changeOvernightFeePriceByParkNumber(dto), HttpStatus.OK);
    }

    @Operation(summary = "Get quantity of history by customer id")
    @GetMapping("/getQuantityOfHistoryByCustomerID/{customerID}")
    public ResponseEntity<Integer> getQuantityOfHistoryByCustomerID(@PathVariable String customerID) {
        return new ResponseEntity<>(service.getQuantityOfHistoryByCustomerID(customerID), HttpStatus.OK);
    }

    @Operation(summary = "Change user parky flag by customer id")
    @PutMapping("/changeUserParkyFlag")
    public ResponseEntity<String> changeUserParkyFlag(@RequestBody ParkyFlagDTO dto) {
        return new ResponseEntity<>(service.changeUserParkyFlag(dto), HttpStatus.OK);
    }

    @Operation(summary = "get user parky flag by customer id")
    @GetMapping("/getUserParkyFlag/{customerID}")
    public ResponseEntity<Boolean> getUserParkyFlagByCustomerID(@PathVariable String customerID) {
        return new ResponseEntity<>(service.getUserParkyFlagByCustomerID(customerID), HttpStatus.OK);
    }

    //--------------Table of Prices-----------------

    @Operation(summary = "Get all price table entries by park id")
    @GetMapping("/getAllPriceTableEntries/{parkId}")
    public ResponseEntity<List<PriceTableEntryDTO>> getAllPriceTableEntriesById(@PathVariable Long parkId) {

        return new ResponseEntity<>(service.getAllPriceTableEntriesById(parkId), HttpStatus.OK);
    }

    @Operation(summary = "Define the Time Periods by park number")
    @PutMapping("/defineTimePeriods/{parkNumber}")
    public ResponseEntity<List<PriceTableEntryDTO>> defineTimePeriods(@PathVariable String parkNumber, @RequestBody List<PriceTableEntryDTO> priceTableEntryDTO) {
        return new ResponseEntity<>(service.defineTimePeriods(parkNumber, priceTableEntryDTO), HttpStatus.OK);
    }


}