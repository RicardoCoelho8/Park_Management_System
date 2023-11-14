package labdsoft.payments_bo_mcs.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jakarta.persistence.*;
import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;
import labdsoft.payments_bo_mcs.model.priceTable.ThresholdCost;
import labdsoft.payments_bo_mcs.model.user.AppUser;
import labdsoft.payments_bo_mcs.model.vehicle.Vehicle;
import labdsoft.payments_bo_mcs.model.vehicle.VehicleType;
import labdsoft.payments_bo_mcs.services.PaymentsService;
import labdsoft.payments_bo_mcs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Set;

@Component
public class Subscribe {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void postConstruct() throws Exception {
        //  this.subscribe("exchange_product"); //define an exchange for your model
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

            switch (exchangeName) {
                case "exchange_user":
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(components[1]);

                        Long userId = jsonObject.getLong("userId");
                        Long nif = jsonObject.getLong("periodStart");

                        ArrayList<Vehicle> vehicles = new ArrayList<>();
                        JSONArray thresholdsArray = jsonObject.getJSONArray("vehicles");
                        for (int j = 0; j < thresholdsArray.length(); j++) {
                            JSONObject vehicleObject = thresholdsArray.getJSONObject(j);
                            String licensePlateNumber = vehicleObject.getString("licensePlateNumber");
                            String vehicleType = vehicleObject.getString("vehicleType");

                            vehicles.add(Vehicle.builder().licensePlateNumber(licensePlateNumber).vehicleType(VehicleType.valueOf(vehicleType)).build());
                        }

                        AppUser user = AppUser.builder().userID(userId).nif(nif).vehicles(vehicles.stream().toList()).build();

                        if (components[0].contains("Created")) {
                            userService.createFromSubscribe(user);
                        } else if (components[0].contains("Updated")) {
                            userService.updateFromSubscribe(user);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}