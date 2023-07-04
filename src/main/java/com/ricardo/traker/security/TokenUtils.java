package com.ricardo.traker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenUtils {

    private static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2592000L;

    @Autowired
    public TokenUtils(@Value("${access.token.validity.seconds}") Long ACCESS_TOKEN_VALIDITY_SECONDS) {
        this.ACCESS_TOKEN_VALIDITY_SECONDS = ACCESS_TOKEN_VALIDITY_SECONDS;
    }

    @Autowired
    UserDetailsService userDetailsService;


    private final static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final static String ACCESS_TOKEN_SECRET = Encoders.BASE64.encode(key.getEncoded());

    public static String createToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("email", userPrincipal.getEmail());
        extra.put("name", userPrincipal.getName());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public UserDetailsImpl getUser(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.replace("Bearer ", "");
            this.validateJwtToken(token);
            return (UserDetailsImpl) userDetailsService.loadUserByUsername(this.getUserNameFromJwtToken(token));
        }
        return null;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
