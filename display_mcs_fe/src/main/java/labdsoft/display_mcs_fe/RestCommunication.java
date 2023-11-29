package labdsoft.display_mcs_fe;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@RefreshScope
public class RestCommunication {

    @Autowired
    private final RestTemplate restTemplate;

    private final String URL = "lb://park20-park-microservice/display/get";

    public DisplayDTO postForPayment(Display sendDTO) {
        ResponseEntity<DisplayDTO> response = this.restTemplate
                .postForEntity(URL, sendDTO, DisplayDTO.class);

        return response.getBody();
    }
}
