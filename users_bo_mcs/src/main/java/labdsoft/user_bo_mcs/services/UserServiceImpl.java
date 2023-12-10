package labdsoft.user_bo_mcs.services;

import labdsoft.user_bo_mcs.communication.Publish;
import labdsoft.user_bo_mcs.model.*;
import labdsoft.user_bo_mcs.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Service
public class UserServiceImpl implements UserService {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Publish publisher;

    @Override
    public UserDTO create(final UserOnCreation userOnCreation) throws Exception {
        // verify if the product already exists
        Password.isValidPreEncodedPassword(userOnCreation.getPassword());

        final Name name = new Name(userOnCreation.getFirstName(), userOnCreation.getLastName());
        final User user = new User(name, new Email(userOnCreation.getEmail()),
                new Password(this.passwordEncoder.encode(userOnCreation.getPassword())),
                new TaxIdNumber(userOnCreation.getNif()), Role.CUSTOMER,
                new Vehicle(userOnCreation.getLicensePlateNumber(), userOnCreation.getVehicleType(),
                        userOnCreation.getVehicleEnergySource()),
                userOnCreation.getPaymentMethod(), UserStatus.ENABLED);

        repository.save(user);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var userDTO = user.toDto();
        String json_user = ow.writeValueAsString(userDTO);

        publisher.publish("exchange_user", "A User was Created | " + json_user, host);

        return user.toDto();
    }

    @Override
    public List<UserDTO> getAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .map(User::toDto).collect(Collectors.toList());
    }

    @Override
    public VehicleOnCreation addVehicle(Long userId, VehicleOnCreation vehicleOnCreation) throws Exception {
        User user = this.repository.findById(userId).orElseThrow();

        Vehicle vehicle = new Vehicle(vehicleOnCreation.getLicensePlateNumber(), vehicleOnCreation.getVehicleType(),
                vehicleOnCreation.getVehicleEnergySource());
        boolean added = user.addVehicle(vehicle);
        if (!added) {
            throw new IllegalArgumentException("Couldn't add vehicle!");
        }
        this.repository.save(user);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var userDTO = user.toDto();
        String json_user = ow.writeValueAsString(userDTO);

        publisher.publish("exchange_user", "A User was Updated | " + json_user, host);
        return vehicleOnCreation;
    }

    @Override
    public Optional<AccessToken> login(UserCredentials credentials) {

        Optional<User> userOpt = this.repository.getUserByEmail(new Email(credentials.getEmail()));
        if (userOpt.isEmpty()
                || !this.passwordEncoder.matches(credentials.getPassword(), userOpt.get().getPassword().password())) {
            return Optional.empty();
        }
        User user = userOpt.get();
        Algorithm algorithm = AlgorithmUtilities.algorithm();
        String accessToken = JWT.create().withSubject(user.getUserId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                .withClaim("role", user.getRole().toString())
                .sign(algorithm);

        return Optional.of(new AccessToken(accessToken, user.getName().getFirstName()));
    }

    @Override
    public PaymentDTO changePaymentMethod(Long userId, PaymentRequest pMethod) throws Exception {
        if (pMethod.getPaymentMethod().equals(PaymentMethod.NOT_DEFINED)) {
            throw new IllegalArgumentException("Can't change payment method back to undefined");
        }
        User user = this.repository.findById(userId).orElseThrow();
        user.setPaymentMethod(pMethod.getPaymentMethod());
        this.repository.save(user);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var userDTO = user.toDto();
        String json_user = ow.writeValueAsString(userDTO);

        PaymentDTO paymentDTO = PaymentDTO.builder().paymentMethod(pMethod.getPaymentMethod()).build();

        publisher.publish("exchange_user", "A User was Updated | " + json_user, host);
        return paymentDTO;
    }

    @Override
    public Set<VehicleOnCreation> getAllUserVehicles(Long userId) {
        Set<VehicleOnCreation> vehiclesOnCreation = new HashSet<>();

        User user = this.repository.findByUserId(userId).orElseThrow();
        Set<Vehicle> vehicles = user.getVehicles();

        for (Vehicle vehicle : vehicles) {
            VehicleOnCreation vehicleOnCreation = VehicleOnCreation.builder()
                    .licensePlateNumber(vehicle.getLicensePlateNumber()).vehicleType(vehicle.getVehicleType())
                    .vehicleEnergySource(vehicle.getVehicleEnergySource()).build();

            vehiclesOnCreation.add(vehicleOnCreation);
        }

        return vehiclesOnCreation;
    }

    @Override
    public PaymentDTO getUserPaymentMethod(Long userId) {
        User user = this.repository.findByUserId(userId).orElseThrow();
        return PaymentDTO.builder().paymentMethod(user.getPaymentMethod()).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addParkiesToUsers(ParkyTransactionRequest request, Long customerManagerId) throws Exception {

        Iterable<User> usersToAdd = this.repository.findAllById(request.getUserIds());

        if (StreamSupport.stream(usersToAdd.spliterator(), false).count() == 0) {
            throw new IllegalArgumentException("Invalid users provided!");
        }

        for (User user : usersToAdd) {
            ParkyTransactionEvent event = new ParkyTransactionEvent(request.getAmount(), request.getReason(),
                    LocalDateTime.now(), customerManagerId);
            if (!user.addParkyTransactionEvent(event)) {
                throw new IllegalArgumentException("Invalid parky transaction!");
            }
        }
        // Check if any mc actually needs the parky updates
        /*
         * ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
         * 
         * for (User userAlreadyAdded : usersToAdd) {
         * var userDTO = userAlreadyAdded.toDto();
         * String json_user = ow.writeValueAsString(userDTO);
         * 
         * publisher.publish("exchange_user", "A User was Updated | " + json_user,
         * host);
         * }
         */ return true;
    }

    @Override
    public ParkyWalletDTO getParkyWalletOfUser(Long userId) throws Exception {
        User user = this.repository.findByUserId(userId).orElseThrow();
        return new ParkyWalletDTO(user.getUserId(), user.getParkies().parkies(), user.getParkies().getParkyEvents());
    }

}
