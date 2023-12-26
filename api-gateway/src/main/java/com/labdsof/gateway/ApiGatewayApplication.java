package com.labdsof.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/users/**")
						.or().path("/userReport/**")
						.uri("lb://park20-user-microservice"))
				.route(p -> p
						.path("/parks/**")
						.or().path("/barriers/**")
						.or().path("/display/**")
						.or().path("/parkReport/**")
						.uri("lb://park20-park-microservice"))
				.route(p -> p
						.path("/payments")
						.or().path("/payments/**")
						.uri("lb://park20-payments-microservice"))
				.build();
	}

}
