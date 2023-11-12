package labdsoft.payments_bo_mcs.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Publish {

    /**
     * Message must in format '(Created, Updated, Removed) NameOfTheObject | ObjectInJSONFormat
     *
     * @param exchangeName
     * @param message
     * @throws Exception
     */
    public static void publish(String exchangeName, String message, String host) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, "fanout");

            channel.basicPublish(exchangeName, "", null, message.getBytes("UTF-8"));
        }
    }


    public static List<PriceTableEntry> getPriceTablePark(String exchangeName, String message, String host) throws Exception {
        try {
            String apiUrl = "https://api.example.com/data";
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            List<PriceTableEntry> entries = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder responseContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }

                // Manually parse the JSON response to List<PriceTableEntry>
                // Implement your JSON parsing logic here

                return null; // Replace with your actual parsing logic
            }

            return entries;
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}