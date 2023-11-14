package labdsoft.payments_bo_mcs.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import labdsoft.payments_bo_mcs.model.priceTable.PriceTableEntry;
import labdsoft.payments_bo_mcs.model.priceTable.ThresholdCost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
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


    @Value("${park_bo}")
    private static String parkBo;

    public static List<PriceTableEntry> getPriceTablePark(String exchangeName, String message, String host) throws Exception {
        try {
            String apiUrl = parkBo + "/parks/getAllPriceTableEntries/" + message;
            URL url = URI.create(apiUrl).toURL();

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

                // Manually parse JSON response
                JSONArray jsonArray = new JSONArray(responseContent.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Extract data from the JSON object and create PriceTableEntry
                    Long parkId = jsonObject.getLong("parkId");
                    String periodStart = jsonObject.getString("periodStart");
                    String periodEnd = jsonObject.getString("periodEnd");

                    // Process the thresholds array
                    ArrayList<ThresholdCost> thresholds = new ArrayList<>();
                    JSONArray thresholdsArray = jsonObject.getJSONArray("thresholds");
                    for (int j = 0; j < thresholdsArray.length(); j++) {
                        JSONObject thresholdObject = thresholdsArray.getJSONObject(j);

                        int thresholdMinutes = jsonObject.getInt("thresholdMinutes");
                        double costPerMinuteAutomobiles = jsonObject.getDouble("costPerMinuteAutomobiles");
                        double costPerMinuteMotorcycles = jsonObject.getDouble("costPerMinuteMotorcycles");

                        thresholds.add(new ThresholdCost(thresholdMinutes, costPerMinuteAutomobiles, costPerMinuteMotorcycles));
                    }

                    // Create PriceTableEntry and add to the list
                    PriceTableEntry entry = new PriceTableEntry(parkId, periodStart, periodEnd, thresholds);
                    entries.add(entry);
                }
            }

            connection.disconnect();
            return entries;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}