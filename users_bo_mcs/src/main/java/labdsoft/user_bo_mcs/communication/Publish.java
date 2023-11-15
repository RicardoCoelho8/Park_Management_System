package labdsoft.user_bo_mcs.communication;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Service
public class Publish {

    /**
     * Message must in format '(Created, Updated, Removed) NameOfTheObject | ObjectInJSONFormat
     *
     * @param exchangeName
     * @param message
     * @throws Exception
     */
    public void publish(String exchangeName, String message, String host) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, "fanout");

            channel.basicPublish(exchangeName, "", null, message.getBytes("UTF-8"));
        }
    }
}