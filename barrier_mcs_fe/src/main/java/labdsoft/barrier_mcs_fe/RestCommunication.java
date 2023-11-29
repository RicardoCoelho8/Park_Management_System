package labdsoft.barrier_mcs_fe;

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

    private final String URL = "lb://park20-park-microservice/barriers/entrance";

    public BarrierDTO postForEntrance(Barrier sendDTO) {
        ResponseEntity<BarrierDTO> response = this.restTemplate
                .postForEntity(URL, sendDTO, BarrierDTO.class);

        return response.getBody();
    }

    public BarrierDTO postForExit(Barrier sendDTO) {
        ResponseEntity<BarrierDTO> response = this.restTemplate
                .postForEntity(URL, sendDTO, BarrierDTO.class);

        return response.getBody();
    }
}
