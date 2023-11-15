package com.labdsof.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;

import reactor.core.publisher.Mono;

/**
 * CustomAuthorizationFilter
 */
@Component
public class CustomAuthenticationFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // lets assume only register user is allowed without authentication
        String apiPath = exchange.getRequest().getPath().pathWithinApplication().toString();
        logger.info("Received request for " + apiPath);
        if (AuthenticationRequestConfig.unauthenticatedRequests.containsKey(apiPath)
                && AuthenticationRequestConfig.unauthenticatedRequests.get(apiPath)
                        .equals(exchange.getRequest().getMethod())) {
            return chain.filter(exchange);
        }
        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.info("Invalid authorization header: " + authorizationHeader);
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }
        JWTVerifier verifier = JWT.require(AlgorithmUtilities.algorithm()).build();
        try {
            DecodedJWT decodedJWT = verifier.verify(authorizationHeader.split("Bearer ")[1]);
            String userId = decodedJWT.getSubject();
            String role = decodedJWT.getClaim("role").asString();
            ServerHttpRequest request = exchange.getRequest()
            .mutate()
            .header("X-UserId", userId)
            .header("X-UserRole", role)
            .build();

            ServerWebExchange mutatedExchange = exchange.mutate().request(request).build();

            return chain.filter(mutatedExchange);
        }
        catch (Exception e) {
            logger.info("Invalid token: " + e.getMessage());

            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();

        }
    }

}