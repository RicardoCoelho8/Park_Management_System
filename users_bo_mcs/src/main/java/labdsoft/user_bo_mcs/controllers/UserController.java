package labdsoft.user_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.user_bo_mcs.communication.Subscribe;
import labdsoft.user_bo_mcs.model.AccessToken;
import labdsoft.user_bo_mcs.model.Role;
import labdsoft.user_bo_mcs.model.UserCredentials;
import labdsoft.user_bo_mcs.model.UserDTO;
import labdsoft.user_bo_mcs.model.UserOnCreation;
import labdsoft.user_bo_mcs.model.VehicleOnCreation;
import labdsoft.user_bo_mcs.services.UserService;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private Subscribe subscribe;

    @Autowired
    private UserService service;

    @Operation(summary = "creates a customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> create(@RequestBody UserOnCreation userBody) {
        logger.info("Received create customer request " + userBody);

        try {
            final UserDTO user = service.create(userBody);
            logger.info("Successfully created user!");
            return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @Operation(summary = "login user")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AccessToken> login(@RequestBody UserCredentials userCredentials) {
        logger.info("Received login request for " + userCredentials.getEmail());

        try {
            final Optional<AccessToken> token = service.login(userCredentials);
            if (token.isEmpty()) {
                logger.info("Invalid credentials provided for " + userCredentials.getEmail());
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            logger.info("Successfully logged in user " + userCredentials.getEmail());
            return new ResponseEntity<AccessToken>(token.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }

    @Operation(summary = "gets all users")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> getAll(
            @RequestHeader("X-UserRole") String userRole) {

        logger.info("Received get all users request");
        if (!Role.SUPERVISOR.toString().equals(userRole)) {
            // only admins can use this endpoint
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        try {
            final List<UserDTO> users = service.getAll();
            logger.info("Successfully retrieved all users!");
            return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @Operation(summary = "Add Vehicle for user")
    @PutMapping("/{userId}/vehicle")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<UserDTO> addUserVehicle(@RequestHeader("X-UserId") String headerUserId,
            @RequestHeader("X-UserRole") String userRole,
            @PathVariable Long userId, @RequestBody VehicleOnCreation vehicle) {
        logger.info("Received add vehicle " + vehicle + " to user " + userId + " request");

        if (!headerUserId.equals(userId.toString())) {
            // only the user that is authenticated in the request is allowed to add a
            // vehicle for himself
            logger.info("Request invalidated due to incoherent path user id of " + userId + "and header user id of " + headerUserId);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        if (!userRole.equals(Role.CUSTOMER.toString())) {
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        try {
            final UserDTO user = service.addVehicle(userId, vehicle);
            logger.info("Successfully added vehicle!");
            return new ResponseEntity<UserDTO>(user, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

}