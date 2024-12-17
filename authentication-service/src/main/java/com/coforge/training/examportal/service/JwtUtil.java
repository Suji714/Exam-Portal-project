package com.coforge.training.examportal.service;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
* Author      : Satyam.3.Singh
* Date        : 14 Dec 2024
* Time        : 10:10:46 am
* Project     : authentication-service
*/

public class JwtUtil {
	
	  private static final String SECRET_KEY = "my_secret_key";
	  
	    public String generateToken(String email, String role) {
	        return Jwts.builder()
	                .setSubject(email)
	                .claim("role", role)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour validity
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	                .compact();
	    }
	 
	    public Claims extractClaims(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }
}
