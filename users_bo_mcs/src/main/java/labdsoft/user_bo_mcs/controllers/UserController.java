package labdsoft.user_bo_mcs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import labdsoft.user_bo_mcs.communication.Subscribe;
import labdsoft.user_bo_mcs.model.*;
import labdsoft.user_bo_mcs.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
            return new ResponseEntity<>(user, HttpStatus.CREATED);
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
            return new ResponseEntity<>(token.get(), HttpStatus.CREATED);
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
        System.out.println(Role.SUPERVISOR.toString().equals(userRole));
        System.out.println(Role.CUSTOMER_MANAGER.toString().equals(userRole));

        if (!((Role.SUPERVISOR.toString().equals(userRole)) || (Role.CUSTOMER_MANAGER.toString().equals(userRole)))) {
            // only admins can use this endpoint
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        try {
            final List<UserDTO> users = service.getAll();
            logger.info("Successfully retrieved all users!");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @Operation(summary = "Add Vehicle for user")
    @PutMapping("/{userId}/vehicle")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<VehicleOnCreation> addUserVehicle(@RequestHeader("X-UserId") String headerUserId,
            @RequestHeader("X-UserRole") String userRole,
            @PathVariable Long userId, @RequestBody VehicleOnCreation vehicle) {
        logger.info("Received add vehicle " + vehicle + " to user " + userId + " request");

        if (!headerUserId.equals(userId.toString())) {
            // only the user that is authenticated in the request is allowed to add a
            // vehicle for himself
            logger.info("Request invalidated due to incoherent path user id of " + userId + "and header user id of "
                    + headerUserId);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        if (!userRole.equals(Role.CUSTOMER.toString())) {
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        try {
            final VehicleOnCreation vehicleOnCreation = service.addVehicle(userId, vehicle);
            logger.info("Successfully added vehicle");
            return new ResponseEntity<>(vehicleOnCreation, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @Operation(summary = "Changes user Payment Method")
    @PutMapping("/{userId}/payment-method")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<PaymentDTO> changePaymentMethod(@RequestHeader("X-UserId") String headerUserId,
            @RequestHeader("X-UserRole") String userRole,
            @PathVariable Long userId, @RequestBody PaymentRequest pMethod) {
        logger.info("Received change payment method to " + pMethod + " of user " + userId + " request");

        if (!headerUserId.equals(userId.toString())) {
            // only the user that is authenticated in the request is allowed to change his
            // own payment method
            logger.info("Request invalidated due to incoherent path user id of " + userId + "and header user id of "
                    + headerUserId);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        if (!userRole.equals(Role.CUSTOMER.toString())) {
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        try {
            final PaymentDTO paymentDTO = service.changePaymentMethod(userId, pMethod);
            logger.info("Successfully changed payment method");
            return new ResponseEntity<>(paymentDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Operation(summary = "gets all user vehicles")
    @GetMapping("/{userId}/vehicles")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Set<VehicleOnCreation>> getAllUserVehicles(@RequestHeader("X-UserId") String headerUserId,
            @RequestHeader("X-UserRole") String userRole,
            @PathVariable Long userId) {

        logger.info("Received get all user vehicles request");
        if (!headerUserId.equals(userId.toString())) {
            // only the user that is authenticated in the request is allowed to get his own
            // vehicles
            logger.info("Request invalidated due to incoherent path user id of " + userId + "and header user id of "
                    + headerUserId);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        if (!userRole.equals(Role.CUSTOMER.toString())) {
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        try {
            final Set<VehicleOnCreation> vehicles = service.getAllUserVehicles(userId);
            logger.info("Successfully retrieved all user vehicles!");
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @Operation(summary = "get user paymentMethod")
    @GetMapping("/{userId}/paymentMethod")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentDTO> getUserPaymentMethod(@RequestHeader("X-UserId") String headerUserId,
            @RequestHeader("X-UserRole") String userRole,
            @PathVariable Long userId) {

        logger.info("Received get all user vehicles request");
        if (!headerUserId.equals(userId.toString())) {
            // only the user that is authenticated in the request is allowed to get his own
            // payment method
            logger.info("Request invalidated due to incoherent path user id of " + userId + "and header user id of "
                    + headerUserId);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        if (!userRole.equals(Role.CUSTOMER.toString())) {
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        try {
            final PaymentDTO paymentDTO = service.getUserPaymentMethod(userId);
            logger.info("Successfully retrieved user payment method!");
            return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @Operation(summary = "Adds parkies to one User or multiple Users")
    @PostMapping("/parkies")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> addParkiesToUsers(@RequestHeader("X-UserId") String headerUserId,
            @RequestHeader("X-UserRole") String userRole,
            @RequestBody ParkyTransactionRequest pRequest) {
        logger.info("Received add parkies request: " + pRequest + " to users " + pRequest.getUserIds());

        if (!userRole.equals(Role.CUSTOMER_MANAGER.toString())) {
            logger.info("Request invalidated due to forbidden user role: " + userRole);
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

        try {
            final Boolean added = service.addParkiesToUsers(pRequest, Long.valueOf(headerUserId));
            if (!added) { // should throw exception but just in case
                return new ResponseEntity<>(false, HttpStatus.CONFLICT);

            }
            logger.info("Successfully added parkies method");
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Operation(summary = "Gets the parky wallet of a user")
    @GetMapping("/parkies/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ParkyWalletDTO> getParkyWalletOfUser(@PathVariable Long userId) {
        logger.info("Received get parkies request of user: " + userId);
        try {
            final ParkyWalletDTO walletDTO = service.getParkyWalletOfUser(userId);
            logger.info("Successfully retrieved parky wallet of user!");
            return new ResponseEntity<>(walletDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }
}