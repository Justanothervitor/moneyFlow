package com.justanothervitor.api_2.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    protected String secret;

    @Value("${jwt.expirationMs}")
    protected Long expiration;

    private Key getSigningKey(){
        byte [] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    public String createToken(Map<String,Object> claims,String subject)
    {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+expiration);

        return Jwts.builder().claims(claims).subject(subject)
                .issuedAt(now).expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

}
