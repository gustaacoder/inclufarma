package com.inclufarma.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.inclufarma.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
    public class TokenService {

        @Value("${api.security.token.secret}")
        private String secret;

        public String generateToken(Usuario usuario){
            try {

                Algorithm algorithm = Algorithm.HMAC256(secret);
                String token = JWT.create()
                        .withIssuer("api-siad")
                        .withSubject(usuario.getEmail()) //Passando o login do usuário para o token
                        .withExpiresAt(getExpirationDate())
                        .sign(algorithm);

                return token;
            } catch (JWTCreationException e) {
                throw new RuntimeException("Error while generating token: ", e);
            }
        }

        public String validateToken(String token){
            try{
                Algorithm algorithm = Algorithm.HMAC256(secret);
                return JWT.require(algorithm)
                        .withIssuer("api-siad")
                        .build()
                        .verify(token)
                        .getSubject();//Pegando o login do usuário no token
            } catch (JWTVerificationException e){
                return null; //Retorna vazio caso o token não seja válido, onde o spring detecta e retorna o erro 401
            }
        }

        private Instant getExpirationDate() {
            return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        }
    }
