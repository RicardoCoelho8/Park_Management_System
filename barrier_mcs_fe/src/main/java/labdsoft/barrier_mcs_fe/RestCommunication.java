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

    private final String URL_ENTRANCE = "lb://park20-park-microservice/barriers/entrance";

    private final String URL_EXIT = "lb://park20-park-microservice/barriers/exit";

    public BarrierDTO postForEntrance(Barrier sendDTO) {
        ResponseEntity<BarrierDTO> response = this.restTemplate
                .postForEntity(URL_ENTRANCE, sendDTO, BarrierDTO.class);

        return response.getBody();
    }

    public BarrierDTO postForExit(Barrier sendDTO) {
        ResponseEntity<BarrierDTO> response = this.restTemplate
                .postForEntity(URL_EXIT, sendDTO, BarrierDTO.class);

        return response.getBody();
    }
}
