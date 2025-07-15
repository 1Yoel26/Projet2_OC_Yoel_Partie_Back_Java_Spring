package com.projet2_oc_y.projet2_oc_y.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtGenerer {
	
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	private static final long tempsExpirationCle = 60 * 60 * 1000;
	
	public static String genererLeTokenJwt(String identifiantUser) {
		
		Date dateNow = new Date();
		
		Date dateExpiration = new Date(dateNow.getTime() + tempsExpirationCle);
		
		 return Jwts.builder()
	                .setSubject(identifiantUser)             // l'identifiant de l'utilisateur (souvent son email ou ID)
	                .setIssuedAt(dateNow)                 // date d’émission
	                .setExpiration(dateExpiration)        // date d’expiration
	                .signWith(key)                    // signature avec clé secrète et algo HS256
	                .compact();                       // retourne le JWT sous forme de String
		
	}

}
