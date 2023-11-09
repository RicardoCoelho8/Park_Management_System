package labdsoft.payments_bo_mcs.services;

import labdsoft.payments_bo_mcs.communication.Publish;
import labdsoft.payments_bo_mcs.model.*;
import labdsoft.payments_bo_mcs.repositories.PaymentsRepository;
import labdsoft.payments_bo_mcs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements PaymentsService {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private PaymentsRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PaymentsDTO create(final PaymentsDTO paymentDTO) throws Exception {
        final Optional<User> verification = userRepository.findByNif(paymentDTO.getNif());
        if (verification.isPresent()) {
            throw new IllegalArgumentException("User does not exist.");
        }

        final Payments p = paymentDTO.toObject();

        repository.save(p);

        Publish.publish("exchange_payment", "A Payment was Created | " + p, host);
        System.out.println("A Payment was Created | " + p.toString());

        return p.toDTO();
    }

    @Override
    public Iterable<PaymentsDTO> findByUserNIF(String nif) {
        Optional<List<Payments>> possibleResult = repository.getPaymentsOfUser(nif);

        return possibleResult.<Iterable<PaymentsDTO>>map(payments -> payments.parallelStream().map(Payments::toDTO).collect(Collectors.toCollection(ArrayList::new))).orElseGet(ArrayList::new);
    }

}
