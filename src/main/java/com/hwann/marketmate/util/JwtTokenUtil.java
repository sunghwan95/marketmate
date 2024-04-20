package com.hwann.marketmate.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private final String secretKey = "mySecretKey";

    public String generateAccessToken(String email) {
        // 30 minutes
        long accessTokenValidity = 1800 * 1000;
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String generateRefreshToken(String email) {
        // 30 days
        long refreshTokenValidity = 30L * 24 * 60 * 60 * 1000;
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) throws Exception {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) throws Exception {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}

