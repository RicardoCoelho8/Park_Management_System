package labdsoft.payments_bo_mcs.services;

import labdsoft.payments_bo_mcs.communication.Publish;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private Publish publisher;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createFromSubscribe(AppUser user) {
        userRepository.save(user);
    }

    @Override
    public void updateFromSubscribe(AppUser user) {
        userRepository.save(user);
    }
}
