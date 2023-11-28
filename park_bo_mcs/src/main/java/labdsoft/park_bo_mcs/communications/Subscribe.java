package labdsoft.park_bo_mcs.communications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import labdsoft.park_bo_mcs.models.user.Customer;
import labdsoft.park_bo_mcs.models.user.UserDTO;
import labdsoft.park_bo_mcs.models.user.Vehicle;
import labdsoft.park_bo_mcs.models.user.VehicleDTO;
import labdsoft.park_bo_mcs.repositories.user.CustomerRepository;
import labdsoft.park_bo_mcs.repositories.user.VehicleRepository;
import labdsoft.park_bo_mcs.services.ParkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Subscribe {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private ParkService parkService;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @PostConstruct
    public void postConstruct() throws Exception {
        this.subscribe("exchange_park"); // define an exchange for your model
        this.subscribe("exchange_user"); // define an exchange for your model

    }

    public void subscribe(String exchangeName) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, exchangeName, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            String[] components = message.split("\\|");
            ObjectMapper objectMapper = new ObjectMapper();

            switch (exchangeName) {
                case "exchange_product":
                    String reviewId = components[1].trim();
                    parkService.createPark(reviewId);
                    break;
                case "exchange_user":
                    UserDTO user = objectMapper.readValue(components[1], UserDTO.class);
                    System.out.println("Received user communication " + user);

                    Customer customer = Customer.builder().customerID(user.getId())
                            .nif(Long.parseLong(String.valueOf(user.getNif())))
                            .name(user.getFirstName() + " " + user.getLastName()).status(user.getUserStatus())
                            .build();

                    customerRepository.save(customer);
                    /* if (possibleCustomer == null) {
                        customerRepository.save(customer);
                    } else {
                        possibleCustomer.setName(user.getFirstName() + " " + user.getLastName());
                        possibleCustomer.setStatus(user.getUserStatus());

                        customerRepository.save(possibleCustomer);
                    } */

                    var vehicles = user.getVehicles();
                    for (VehicleDTO vehicle : vehicles) {
                        if (vehicleRepository.getVehicleByPlateNumber(vehicle.getLicensePlateNumber()) == null) {
                            vehicleRepository.save(Vehicle.builder().customerID(customer.getCustomerID())
                                    .plateNumber(vehicle.getLicensePlateNumber())
                                    .vehicleEnergySource(vehicle.getVehicleEnergySource())
                                    .vehicleType(vehicle.getVehicleType()).build());
                        }
                    }

                    break;
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}