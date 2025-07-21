package com.projet2_oc_y.projet2_oc_y.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.security.JwtGenerer;
import com.projet2_oc_y.projet2_oc_y.service.UsersService;


@RestController
public class UsersController {
	
	@Autowired
	private UsersService userService;
	
	//route 1
	@PostMapping("api/auth/register")
	public ResponseEntity<?> reponseHttpCreationCompte(@RequestBody Users infoUser){
		
		// si le mail est déjà pris en Bdd, retourn erreur:
		if(!userService.verificationEmailUnique(infoUser)) {
			return ResponseEntity.badRequest().body(new HashMap<>());
		}
		
		// si il n'est pas pris, retourné le token
		String tokenJwt = JwtGenerer.genererLeTokenJwt(infoUser.getEmail());
		
		HashMap<String, String> body = new HashMap<>();
		
		body.put("token", tokenJwt);
		
		return ResponseEntity.ok(body);
	}
	
	
	
	@PostMapping("api/auth/login")
	public ResponseEntity<?>reponseHttpConnectionCompte(@RequestBody Users infoDeConnection){
		
		String id = infoDeConnection.getEmail();
		String mdp = infoDeConnection.getPassword();
		
		if(userService.verificationEmailEtMdpCorrect(id, mdp)) {
			
			String tokenJwt = JwtGenerer.genererLeTokenJwt(id);
			
			HashMap<String, String> body = new HashMap<>();
			
			body.put("token", tokenJwt);
			
			return ResponseEntity.ok(body);
		}
		
		// si id ou mdp incorrect:
		
		HashMap<String, String> body = new HashMap<>();
		
		body.put("message", "error");
		
		return ResponseEntity.status(401).body(body);
		
		
	}
	
	

}
