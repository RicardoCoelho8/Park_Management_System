package com.labdsof.gateway;

import com.auth0.jwt.algorithms.Algorithm;

public class AlgorithmUtilities {
    
    private static final Algorithm algorithm = Algorithm.HMAC256("jhewiwrvmqere3".getBytes()); 

    public static Algorithm algorithm() {
        return algorithm;
    }

}
