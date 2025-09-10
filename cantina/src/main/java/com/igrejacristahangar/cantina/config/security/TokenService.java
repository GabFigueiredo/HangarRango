package com.igrejacristahangar.cantina.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.igrejacristahangar.cantina.modules.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        var algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.create()
                    .withIssuer("IGREJA_CRISTA_HANGAR")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(generateExpireDate())
                    .sign(algorithm);
        } catch(JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }

    }

    public String validateToken(String token) {
        var algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .withIssuer("IGREJA_CRISTA_HANGAR")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Token inválido", exception);
        }
    }

    private Instant generateExpireDate() {
        return LocalDateTime.now()
                .plusHours(3)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
