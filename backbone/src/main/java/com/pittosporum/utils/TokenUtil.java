package com.pittosporum.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pittosporum.dto.UserDto;

import java.util.Date;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

public class TokenUtil {
    private TokenUtil(){}

    private static final long EXPIRE_TIME = 10 * 60 * 1000;

    public static boolean verify(String token, String userName, String secret){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userName", userName)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String generateToken(UserDto userDto) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(userDto.getPwd());
        return JWT.create()
                .withClaim("userName", userDto.getName())
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
