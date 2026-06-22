package com.MyNicProject.hrms.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "nic-hrms-super-secret-key-that-is-at-least-256-bits-long";
    private static final String ISSUER = "NextGen-HRMS";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds

    public String generateToken(String employeeId, String role) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withClaim("employeeId", employeeId)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(ALGORITHM);
    }

    public DecodedJWT verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
    }
}
