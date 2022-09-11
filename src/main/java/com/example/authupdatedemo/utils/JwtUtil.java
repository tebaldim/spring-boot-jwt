package com.example.authupdatedemo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtil {

    private static final int expireInMs = 10 * 60 * 1000;
    private static final String jwtSecret = "NcNYf0mTorpKOXXqkuJqCbiQfeUWLyG0Xq6i3MUc";

    public String generate(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("myIssuer")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireInMs))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(jwtSecret.getBytes()))
                .compact();
    }

    public boolean validate(String token) {
        return getUsername(token) != null && isNotExpired(token);
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isNotExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(jwtSecret.getBytes())).parseClaimsJws(token).getBody();
    }
}
