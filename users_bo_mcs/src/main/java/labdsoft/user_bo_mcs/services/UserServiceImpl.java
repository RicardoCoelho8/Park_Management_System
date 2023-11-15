package labdsoft.user_bo_mcs.services;

import labdsoft.user_bo_mcs.communication.Publish;
import labdsoft.user_bo_mcs.model.AccessToken;
import labdsoft.user_bo_mcs.model.Email;
import labdsoft.user_bo_mcs.model.Name;
import labdsoft.user_bo_mcs.model.Password;
import labdsoft.user_bo_mcs.model.Role;
import labdsoft.user_bo_mcs.model.TaxIdNumber;
import labdsoft.user_bo_mcs.model.User;
import labdsoft.user_bo_mcs.model.UserCredentials;
import labdsoft.user_bo_mcs.model.UserDTO;
import labdsoft.user_bo_mcs.model.UserOnCreation;
import labdsoft.user_bo_mcs.model.Vehicle;
import labdsoft.user_bo_mcs.model.VehicleOnCreation;
import labdsoft.user_bo_mcs.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
                userOnCreation.getAccountNumber(), new TaxIdNumber(userOnCreation.getNif()), Role.CUSTOMER);

        repository.save(user);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json_user = ow.writeValueAsString(user);

        publisher.publish("exchange_user", "A User was Created | " + json_user, host);

        return user.toDto();
    }

    @Override
    public List<UserDTO> getAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .map(User::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO addVehicle(Long userId, VehicleOnCreation vehicleOnCreation) throws Exception {
        User user = this.repository.findById(userId).orElseThrow();

        Vehicle vehicle = new Vehicle(vehicleOnCreation.getLicensePlateNumber(), vehicleOnCreation.getVehicleType());
        boolean added = user.addVehicle(vehicle);
        if (!added) {
            throw new IllegalArgumentException("Couldn't add vehicle!");
        }
        this.repository.save(user);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json_user = ow.writeValueAsString(user);

        publisher.publish("exchange_user", "A User was Updated | " + json_user, host);

        return user.toDto();

    }

    @Override
    public Optional<AccessToken> login(UserCredentials credentials) throws Exception {

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

        return Optional.of(new AccessToken(accessToken));
    }

}
