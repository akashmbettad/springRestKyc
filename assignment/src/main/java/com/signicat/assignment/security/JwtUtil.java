package com.signicat.assignment.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${app.secret}")
	private String secretKey;

	//method to generate token
	public String generateToken(String user) {

		String jwtToken=Jwts.builder()
				.setSubject(user)
				.setIssuer(secretKey)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
				.compact();

		return jwtToken;
	}


	//utility method to get claims
	public Claims readClaims(String token) {

		return Jwts.parser().setSigningKey(secretKey.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}

	//Get token expiration date
	public Date getExpiryDate(String token) {
		Date expDate=readClaims(token).getExpiration();
		return expDate;
	}

	//read subject or user name
	public String getUsername(String token) {
		
		return readClaims(token).getSubject();
	}

	//get token validity
	public boolean isTokenExpired(String token) {
		Boolean isExpired=getExpiryDate(token).before(new Date(System.currentTimeMillis()));
		return isExpired;
	}
	
	//token validation - validate user name in token and database, also expiry date
	public boolean validateToken(String token,String username) {
		
		String tokenUsername=getUsername(token);
		return(username.equals(tokenUsername)&&!isTokenExpired(token));
	}
	
}
