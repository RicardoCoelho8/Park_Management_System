package labdsoft.user_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.user_bo_mcs.model.UserDTO;
import labdsoft.user_bo_mcs.model.UserOnCreation;
import labdsoft.user_bo_mcs.model.VehicleOnCreation;
import labdsoft.user_bo_mcs.services.UserService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@Tag(name = "User", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //@Autowired
   // private Subscribe subscribe;

    @Autowired
    private UserService service;

    @Operation(summary = "creates a user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> create(@RequestBody UserOnCreation userBody) {
        logger.info("Received create user request " + userBody);

        try {
            final UserDTO user = service.create(userBody);
            logger.info("Successfully created user!");
            return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
        }
        catch( Exception e ) {
            logger.info(e.getMessage());
            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }
    @Operation(summary = "gets all users")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> getAll() {
        logger.info("Received get all users request");
        try {
            final List<UserDTO> users = service.getAll();
            logger.info("Successfully retrieved all users!");
            return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
        }
        catch( Exception e ) {
            logger.info(e.getMessage());
            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @Operation(summary = "Add Vehicle for user")
    @PutMapping("/{userId}/vehicle")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> addUserVehicle(@PathVariable Long userId, @RequestBody VehicleOnCreation vehicle) {
        logger.info("Received add vehicle " + vehicle + " to user " + userId + " request");
        try {
            final UserDTO user = service.addVehicle(userId, vehicle);
            logger.info("Successfully added vehicle!");
            return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
        }
        catch( Exception e ) {
            logger.info(e.getMessage());
            throw new ResponseStatusException( HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

}