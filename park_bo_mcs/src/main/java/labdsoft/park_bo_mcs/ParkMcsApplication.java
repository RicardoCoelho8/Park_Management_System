package labdsoft.park_bo_mcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParkMcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkMcsApplication.class, args);
    }

}
