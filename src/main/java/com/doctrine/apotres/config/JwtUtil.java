package com.doctrine.apotres.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${app.jwt.secret:DoctrineDesApotres@2026#SecureKey!JWT$Token*Spring&Boot%Admin}")
    private String jwtSecret;

    @Value("${app.jwt.expiration:86400000}")
    private long jwtExpiration;

    public String genererToken(String username) {

        return Jwts.builder()
            
            .setSubject(username)

           
            .setIssuedAt(new Date())

            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))

            .signWith(getCleSecrete(), SignatureAlgorithm.HS256)

            .compact();
    }

    public String extraireUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getCleSecrete())  
            .build()
            .parseClaimsJws(token)          
            .getBody()
            .getSubject();                  
    }

  
    public boolean validerToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getCleSecrete())
                .build()
                .parseClaimsJws(token);
            
            return true;

        } catch (ExpiredJwtException e) {
           
            System.out.println("Token JWT expiré : " + e.getMessage());
        } catch (MalformedJwtException e) {
            
            System.out.println("Token JWT invalide : " + e.getMessage());
        } catch (SignatureException e) {
           
            System.out.println("Signature JWT invalide : " + e.getMessage());
        } catch (IllegalArgumentException e) {

            System.out.println("Token JWT vide : " + e.getMessage());
        }
        return false;
    }

 
    private Key getCleSecrete() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
