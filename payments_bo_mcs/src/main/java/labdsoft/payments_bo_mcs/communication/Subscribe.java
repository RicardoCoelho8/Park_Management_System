package labdsoft.payments_bo_mcs.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.user.UserDTO;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleDTO;
import labdsoft.payments_bo_mcs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Subscribe {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void postConstruct() throws Exception {
        this.subscribe("exchange_payment");
        this.subscribe("exchange_user");
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
                case "exchange_user":
                    UserDTO user = objectMapper.readValue(components[1], UserDTO.class);

                    ArrayList<Vehicle> vehicles = new ArrayList<>();

                    for (VehicleDTO vehicleDTO : user.getVehicles()) {
                        vehicles.add(Vehicle.builder()
                                .plateNumber(vehicleDTO.getLicensePlateNumber())
                                .vehicleEnergySource(vehicleDTO.getVehicleEnergySource())
                                .vehicleType(vehicleDTO.getVehicleType()).build());
                    }

                    AppUser appUser = AppUser.builder()
                            .nif(Long.parseLong(user.getNif() + ""))
                            .vehicles(vehicles)
                            .totalParkies(user.getTotalParkies())
                            .build();

                    System.out.println("User received: " + appUser.toString());
                    userService.createFromSubscribe(appUser);
                    break;
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}