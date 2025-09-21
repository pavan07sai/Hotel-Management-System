package com.CaseStudy.Api_gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
@Component
public class JwtUtil {

    private static String secretKey = "nHt5kFpFJmS35OJ3Q/ZMAWfU0VxZP9S+q38HO1lWl1A=";  // Must be 256-bit (32+ chars)
    private static Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) 
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

  
    public String extractRole(String token) {
        return extractClaims(token).get("roles", String.class);
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    
    public boolean validateToken(String token) {
//        return extractUsername(token).equals(username);
    	return extractClaims(token).getExpiration().before(new Date()) && !extractClaims(token).getExpiration().equals(new Date()); 
    }
    

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    
}
