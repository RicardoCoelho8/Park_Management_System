package com.labdsof.gateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class AuthenticationRequestConfig {
    public static final Map<String, HttpMethod> unauthenticatedRequests = new HashMap<>() {
        {
            put("/users", HttpMethod.POST);
            put("/users/login", HttpMethod.POST);
            put("/barriers/entrance", HttpMethod.POST);
            put("/barriers/exit", HttpMethod.POST);
            put("/display/update", HttpMethod.POST);
            put("/display/get", HttpMethod.POST);
            put("/payments", HttpMethod.POST);
        }
    };
}
