package labdsoft.park_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.park_bo_mcs.communications.Subscribe;
import labdsoft.park_bo_mcs.dtos.park.BarrierDisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayGetDTO;
import labdsoft.park_bo_mcs.dtos.park.DisplayUpdateDTO;
import labdsoft.park_bo_mcs.services.DisplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Display", description = "Endpoints for managing displays")
@RestController
@RequestMapping("/display")
class DisplayController {
    private static final Logger logger = LoggerFactory.getLogger(DisplayController.class);

    @Autowired
    private Subscribe subscribe;

    @Autowired
    private DisplayService service;

    @Operation(summary = "Update Display")
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BarrierDisplayDTO> updateDisplayMessage(@RequestBody DisplayUpdateDTO displayUpdateDTO) {
        final BarrierDisplayDTO barrierDisplayDTO = service.updateDisplayMessage(displayUpdateDTO);
        return new ResponseEntity<>(barrierDisplayDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get Display")
    @PostMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DisplayDTO> getDisplayMessage(@RequestBody DisplayGetDTO displayGetDTO) {
        final DisplayDTO displayDTO = service.getDisplayMessage(displayGetDTO);
        return new ResponseEntity<>(displayDTO, HttpStatus.OK);
    }
}