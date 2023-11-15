package labdsoft.park_bo_mcs.communications;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

public class Publish {

    public static void publish(String exchangeName, String message, String host) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, "fanout");

            channel.basicPublish(exchangeName, "", null, message.getBytes("UTF-8"));
        }
    }
}