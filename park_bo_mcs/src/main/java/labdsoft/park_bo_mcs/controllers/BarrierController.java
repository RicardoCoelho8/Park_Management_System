package labdsoft.park_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.park_bo_mcs.dtos.BarrierLicenseReaderDTO;
import labdsoft.park_bo_mcs.dtos.EntranceBarrierDTO;
import labdsoft.park_bo_mcs.dtos.ExitBarrierDTO;
import labdsoft.park_bo_mcs.services.BarrierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Barrier", description = "Endpoints for managing barriers")
@RestController
@RequestMapping("/barriers")
class BarrierController {
    private static final Logger logger = LoggerFactory.getLogger(BarrierController.class);

    @Autowired
    private BarrierService service;

    @Operation(summary = "Entrance Optical Reader")
    @PostMapping("/entrance")
    public ResponseEntity<EntranceBarrierDTO> entranceOpticalReader(@RequestBody BarrierLicenseReaderDTO barrierLicenseReaderDTO) {

        final EntranceBarrierDTO entranceBarrierDTO = service.entranceOpticalReader(barrierLicenseReaderDTO);
        return new ResponseEntity<>(entranceBarrierDTO, HttpStatus.OK);
    }

    @Operation(summary = "Exit Optical Reader")
    @PostMapping("/exit")
    public ResponseEntity<ExitBarrierDTO> exitOpticalReader(@RequestBody BarrierLicenseReaderDTO barrierLicenseReaderDTO) {

        final ExitBarrierDTO exitBarrierDTO = service.exitOpticalReader(barrierLicenseReaderDTO);
        return new ResponseEntity<>(exitBarrierDTO, HttpStatus.OK);
    }
}