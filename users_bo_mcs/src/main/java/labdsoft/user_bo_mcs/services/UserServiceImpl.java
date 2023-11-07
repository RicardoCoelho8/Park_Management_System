package labdsoft.user_bo_mcs.services;

import labdsoft.user_bo_mcs.communication.Publish;
import labdsoft.user_bo_mcs.model.Email;
import labdsoft.user_bo_mcs.model.Name;
import labdsoft.user_bo_mcs.model.Password;
import labdsoft.user_bo_mcs.model.TaxIdNumber;
import labdsoft.user_bo_mcs.model.User;
import labdsoft.user_bo_mcs.model.UserDTO;
import labdsoft.user_bo_mcs.model.UserOnCreation;
import labdsoft.user_bo_mcs.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO create(final UserOnCreation userOnCreation) throws Exception {
        //verify if the product already exists
        Password.isValidPreEncodedPassword(userOnCreation.getPassword());

        final Name name = new Name(userOnCreation.getFirstName(), userOnCreation.getLastName());
        final User u = new User(name, new Email(userOnCreation.getEmail()), new Password(this.passwordEncoder.encode(userOnCreation.getPassword())), userOnCreation.getAccountNumber(), new TaxIdNumber(userOnCreation.getNif()));

        repository.save( u);

        Publish.publish("exchange_user", "A User was Created | " + u, host);



        return u.toDto();
    }

    @Override
    public List<UserDTO> getAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
            .map(User::toDto).collect(Collectors.toList());
    }


}
