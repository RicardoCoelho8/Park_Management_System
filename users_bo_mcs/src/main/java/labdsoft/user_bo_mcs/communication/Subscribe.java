package labdsoft.user_bo_mcs.communication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import labdsoft.user_bo_mcs.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Subscribe {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @PostConstruct
    public void postConstruct() throws Exception {
        this.subscribe("exchange_user"); //define an exchange for your model
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
                    User user = objectMapper.readValue(components[1], User.class);
                    System.out.println("Received user communication " + user);
                    break;
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}