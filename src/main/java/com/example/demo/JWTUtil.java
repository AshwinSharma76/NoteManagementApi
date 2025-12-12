package com.example.demo;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

	private String keycode = "IM_ASHWIN_IM_LEARNING_JAVA_AND_SPRING_BOOT_AT_PPOINT_123456";
	private SecretKey key = Keys.hmacShaKeyFor(keycode.getBytes());
	private int expiredTime = 1000 * 60 * 60;

	public String genrateToken(String username,int uid) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date()).claim("userid", uid)
				.setExpiration(new Date(System.currentTimeMillis() + expiredTime))
				.signWith(key, SignatureAlgorithm.HS256).compact();

	}

	public String extractUserName(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
	}

	public int extractUserId(String token) {
		Claims claim= Jwts.parserBuilder()
				.setSigningKey(key).build().parseClaimsJws(token).getBody();
		
		return ((Integer) claim.get("userid")).intValue();
	}

	public boolean isTokenExpired(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration()
				.before(new Date());

	}

	public boolean isTokenValid(String username, UserDetails userDetails, String token) {
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

}
