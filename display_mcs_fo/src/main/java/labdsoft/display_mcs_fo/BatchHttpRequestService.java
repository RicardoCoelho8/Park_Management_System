package labdsoft.display_mcs_fo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class BatchHttpRequestService {
    private static final Logger logger = LoggerFactory.getLogger(BatchHttpRequestService.class);


    @Value("${barrier.id}")
    private String barrierId;

    @Autowired
    private final RestTemplate restTemplate;
    private String oldMessage;

    public BatchHttpRequestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.oldMessage = "";
    }

    @Scheduled(fixedRate = 1000) // Run every 10 seconds
    public void makeHttpRequest() {
        try {
            String apiUrl = "http://localhost:8080/display/get";

            ResponseEntity<DisplayDTO> response = this.restTemplate
                    .postForEntity(apiUrl, Display.builder().id(barrierId).build(), DisplayDTO.class);
            // Handle the response as needed
            logger.info("HTTP Request successful. Response: {}", response);

            if(Objects.requireNonNull(response.getBody()).getMessage().equals(oldMessage)){
                System.out.println(response.getBody());

                oldMessage = response.getBody().getMessage();
            }else{
                oldMessage = "";
            }
        } catch (Exception e) {
            System.err.println("Error making HTTP request: " + e.getMessage());
        }
    }
}
