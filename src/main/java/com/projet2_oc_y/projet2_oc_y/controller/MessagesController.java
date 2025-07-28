package com.projet2_oc_y.projet2_oc_y.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.model.Messages;
import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.service.MessagesService;
import com.projet2_oc_y.projet2_oc_y.service.UsersService;

@RestController
public class MessagesController {
	
	@Autowired
	private MessagesService messageService;
	
	@Autowired
	private UsersService userService;
	
	@PostMapping("api/messages")
	public ResponseEntity<?> insertionMessage(@RequestBody Messages infoDuMessage, Authentication authentication){
		
		// question pour le mentor : c'est moi qui doit recuperer l'id_user, ou il est donné dans le body ? 
		
		
		// si pas connecté, retourne erreur 403 automatiquement par Spring Security.
		
		
		// si connecté, on valide le message, puis on l'insere en Bdd:
		if(messageService.validationMessage(infoDuMessage)) {
			
			// recup de l'user connecté:
			Users userConnecte = userService.retourneUserConnecte(authentication.getName());
			
			// recup de son id:
			int id_user_connecte = userConnecte.getId();
			
			// ajout de l'id_user de l'user connecté pour le message:
			infoDuMessage.setUserId(id_user_connecte);
			
			
			messageService.insertionMessage(infoDuMessage);
			
			HashMap<String, String> body = new HashMap<>();
	        body.put("message", "Message send with success");
	        
			
			return ResponseEntity.ok(body);
			
			
		}
		// si connecté mais message non validé :
		else {
			
			return ResponseEntity.status(400).body(new HashMap<>());
			
		}
		
		
	}
		

}
