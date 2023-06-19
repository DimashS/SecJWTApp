package com.dimash.securityApp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    // генерация и валидация токена
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String username) {
        // generate чтобы отдать
        // Instant представляет момент времени в мировом координированном времени (UTC).
        // Date.from(...): Создает объект Date из Instant. Date представляет дату и время в определенной временной зоне.
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        return JWT.create() // вызовет создание нового JWT token
                .withSubject("User details") // значение поля установит, значение укажет свзязь и что содержит поле JWT
                // добавится в payload нашего токена
                .withClaim("username", username)
                .withIssuedAt(new Date()) // kogda
                .withIssuer("me") // кто выдал
                .withExpiresAt(expirationDate) // plus  60 minutes
                .sign(Algorithm.HMAC256(secret)); // подписываем и отправляем клиенту, тут будет его имя
    }

    // вызываем метод когда клиент шлет запрос с JWT Token и мы должны валидировать (проверить) его
    // извлечь claim и найти по базе и понять кто это
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        // JWTVerifier используется для проверки подписи и других утверждений в токене
        // через require мы указываем требования к подписи (require - требовать)
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details") // должен иметь такое поле пришедший Токен
                .withIssuer("me").build(); // поле с тем кто выдал такое
        // верификация JWT token, если все ок то достанем имя пользователя в виде строки
        DecodedJWT jwt = jwtVerifier.verify(token); // содержит подробности о токене, если вериф токен пройдет то вернет
        // этот с подробностями о токене
        return jwt.getClaim("username").asString(); // получим username
    }

}