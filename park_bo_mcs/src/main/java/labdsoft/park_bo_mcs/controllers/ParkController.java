package labdsoft.park_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.park_bo_mcs.communications.Subscribe;
import labdsoft.park_bo_mcs.dtos.NearbyParkOccupancyDTO;
import labdsoft.park_bo_mcs.dtos.OccupancyParkDTO;
import labdsoft.park_bo_mcs.dtos.PriceTableEntryDTO;
import labdsoft.park_bo_mcs.services.ParkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "Get all price table entries by park id")
    @GetMapping("/getAllPriceTableEntries/{parkId}")
    public ResponseEntity<List<PriceTableEntryDTO>> getAllPriceTableEntriesById(@PathVariable Long parkId) {

        return new ResponseEntity<>(service.getAllPriceTableEntriesById(parkId), HttpStatus.OK);
    }

    @Operation(summary = "Get all nearby parks occupancy and distance")
    @GetMapping("/real-time/occupancy/{userLatitude}/{userLongitude}/{maxDistanceKm}")
    public ResponseEntity<List<NearbyParkOccupancyDTO>> getRealTimeNearbyParksOccupancy(@PathVariable double userLatitude, @PathVariable double userLongitude, @PathVariable double maxDistanceKm) {
        logger.info("Received get all nearby parks occupancy and distance request");
        return new ResponseEntity<>(service.getRealTimeNearbyParksOccupancy(userLatitude, userLongitude, maxDistanceKm), HttpStatus.OK);
    }

}