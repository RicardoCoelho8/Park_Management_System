package com.labdsof.gateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class AuthenticationRequestConfig {
    public static final Map<String, HttpMethod> unauthenticatedRequests = new HashMap<>() {
        {
            put("/users", HttpMethod.POST);
            put("/users/login", HttpMethod.POST);
        }
    };

}
