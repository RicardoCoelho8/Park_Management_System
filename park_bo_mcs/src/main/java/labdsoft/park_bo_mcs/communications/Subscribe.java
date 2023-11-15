package labdsoft.park_bo_mcs.communications;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import jakarta.annotation.PostConstruct;
import labdsoft.park_bo_mcs.services.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Subscribe {
    @Autowired
    private ParkService parkService;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @PostConstruct
    public void postConstruct() throws Exception {
        this.subscribe("exchange_park"); //define an exchange for your model
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
                case "exchange_product":
                    String reviewId = components[1].trim();
                    parkService.createPark(reviewId);
                    break;
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}