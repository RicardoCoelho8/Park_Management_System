package labdsoft.barrier_mcs_fe;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class BarrierCarPlateReader {

    private static final Logger logger = LoggerFactory.getLogger(BarrierCarPlateReader.class);

    @Autowired
    private RestCommunication restCommunication;

    @Value("${barrier.type}")
    private String barrierType;

    @Value("${barrier.id}")
    private Long barrierId;

    @Value("${park.id}")
    private Long parkID;

    @Value("${park.number}")
    private Long parkNumber;

    private boolean isOpen = false;
    private boolean started = true;

    private final Scanner scanner = new Scanner(System.in);
    private final Pattern licensePlatePattern = Pattern.compile("^(([A-Z]{2}-\\d{2}-(\\d{2}|[A-Z]{2}))|(\\d{2}-(\\d{2}-[A-Z]{2}|[A-Z]{2}-\\d{2})))$");

    @Scheduled(cron = "0/10 * * * * ?") // Run every 10 seconds
    public void checkLicensePlate() {
        try {

            if (started) {
                logger.info("----------------------------------------");
                logger.info("Waiting for a car to approach the barrier:");
                started = false;
            }

            if (isOpen) {
                logger.info("....");
                logger.info("Closing barrier.");
                logger.info("");

                logger.info("----------------------------------------");
                logger.info("Waiting for a car to approach the barrier:");
            }

            String input = scanner.nextLine();
            Matcher matcher = licensePlatePattern.matcher(input);

            if (!Objects.equals(input, "")) {
                logger.info("License plate number: " + input);

                if (!matcher.matches()) {
                    logger.info("Invalid license plate number!");
                    started = true;
                } else {
                    logger.info("Barrier opened for car with license plate number: " + input);
                    if ("entrance".equalsIgnoreCase(barrierType)) {
                        restCommunication.postForEntrance(new Barrier(barrierId, parkID, parkNumber, input, Calendar.getInstance()));
                    } else if ("exit".equalsIgnoreCase(barrierType)) {
                        restCommunication.postForExit(new Barrier(barrierId, parkID, parkNumber, input, Calendar.getInstance()));
                    }

                    isOpen = true;
                }
            } else {
                isOpen = false;
            }
        } catch (Exception e) {
            logger.info("Error checking lincense plate: " + e.getMessage());
        }
    }
}
