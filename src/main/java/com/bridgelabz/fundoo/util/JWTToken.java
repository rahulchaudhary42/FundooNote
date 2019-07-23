package com.bridgelabz.fundoo.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JWTToken {

	private String secretPin = "##9999882399";

	public String generateToken(String id) {

		String token = Jwts.builder().setSubject("fundooNotes")
				.setExpiration(new Date(System.currentTimeMillis() + 86400000)).setId(id)
				.signWith(SignatureAlgorithm.HS256, secretPin).compact();
		return token;
	}

	public String verifyToken(String token) {

		Jws<Claims> claims = Jwts.parser().setSigningKey(secretPin).parseClaimsJws(token);
		String userId = claims.getBody().getId();
		return userId;

	}

}
