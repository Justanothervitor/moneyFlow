package com.Api.MoneyFlow.MainCfg.JwtCfg;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.Api.MoneyFlow.SecurityServices.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@PropertySource(value= {"classpath:application-dev.properties"})
@Component
public class JwtUtils{
	
		private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
		
		@Value("${jwt.JwtSecret}")
		protected String jwtSecret;
		
		@Value("${jwt.JwtExpirationMs}")
		protected int expirationMs;
		
		@Getter
		private String token;
		
		public JwtUtils (final String token)
		{
			this.token = token;
		}
		
		public JwtUtils()
		{
			
		}
		
		protected Key key()
		{
			return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		}
		
		public String getUsernameFromJwtToken(String token)
		{
                return Jwts.parserBuilder().setSigningKey(key()).build()
                        .parseClaimsJws(token).getBody().getSubject();
		}
		
		public String generateJwtToken(Authentication authentication) {
			
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			return this.token = Jwts.builder()
					.setSubject((userPrincipal.getUsername()))
					.setIssuedAt(new Date())
					.setExpiration(new Date((new Date().getTime()+expirationMs)))
					.signWith(key(),SignatureAlgorithm.HS256)
					.compact();		
		}

		protected String returnActualJwt()
		{
			if(validateJwtToken(token))
			{
				return token;
			}
			else{
				return null;
			}
		}

		
		protected boolean validateJwtToken(String authToken)
		{
			try {
				Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
				return true;
			}catch(MalformedJwtException e)
			{
				logger.error("Invalid JWT token: {}", e.getMessage());
		    } catch (ExpiredJwtException e) {
		      logger.error("JWT token is expired: {}", e.getMessage());
		    } catch (UnsupportedJwtException e) {
		      logger.error("JWT token is unsupported: {}", e.getMessage());
		    } catch (IllegalArgumentException e) {
		      logger.error("JWT claims string is empty: {}", e.getMessage());
		    }
			return false;
		}
	
}