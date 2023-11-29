package labdsoft.display_mcs_fe;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class BatchHttpRequestService {

    private static final Logger logger = LoggerFactory.getLogger(BatchHttpRequestService.class);

    @Autowired
    private RestCommunication restCommunication;

    @Value("${display.id}")
    private String barrierId;

    private Integer oldOccupancy;
    private String oldMessage;

    @Scheduled(cron = "0/10 * * * * ?") // Run every 10 seconds
    public void makeHttpRequest() {
        try {
            Display display = Display.builder().displayID(Long.parseLong(barrierId)).build();

            DisplayDTO response = restCommunication.postForPayment(display);

            if (response.getMessage() != null) {
                if (!response.getMessage().equals(oldMessage) || !Objects.equals(response.getOccupancy(), oldOccupancy)) {
                    logger.info("Display: " + response.getMessage());
                    logger.info("Occupancy: " + response.getOccupancy());

                    oldMessage = response.getMessage();
                    oldOccupancy = response.getOccupancy();
                }
            }

        } catch (Exception e) {
            logger.info("Error making HTTP request: " + e.getMessage());
        }
    }
}
