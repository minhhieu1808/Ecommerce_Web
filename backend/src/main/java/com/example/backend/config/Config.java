package com.example.backend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.text.SimpleDateFormat;

public class Config {
    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy/MM/dd");

    public static final Algorithm JWT_ALGORITHM = Algorithm.HMAC256("hieunm");
    public static final JWTVerifier JWT_VERIFIER = JWT.require(JWT_ALGORITHM )
            .withIssuer("auth")
            .build();
}
