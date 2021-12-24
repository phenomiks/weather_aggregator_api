package ru.geekbrains.api.auth_api.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiredTime}")
    private long expiredTime;

    public String generateToken(String login) {
        Date issuedDate = new Date();
        Date expired = new Date(issuedDate.getTime() + expiredTime * 60 * 1000);

        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(issuedDate)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(secret))
                .compact();
    }
}
