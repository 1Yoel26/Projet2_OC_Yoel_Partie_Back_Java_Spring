package com.projet2_oc_y.projet2_oc_y.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.model.Messages;
import com.projet2_oc_y.projet2_oc_y.security.JwtGenerer;
import com.projet2_oc_y.projet2_oc_y.service.MessagesService;

@RestController
public class MessagesController {
	
	@Autowired
	private MessagesService messageService;
	
	public ResponseEntity<?> reponseHttpMessage(@RequestBody Messages infoDuMessage){
		
		boolean connecte = true;
		
		if(connecte) {
			
			// si connecté + message validé : http = 200
			if(messageService.validationMessage(infoDuMessage)) {
				
				String tokenJwt = JwtGenerer.genererLeTokenJwt(messageService.getEmail(1));
				
				HashMap<String, String> body = new HashMap<>();
				
				body.put("message", "Message send with success");
				
				return ResponseEntity.ok(body);
				
			}
			
			// si connecté + message non validé : http = 400
			else {
				return ResponseEntity.status(400).body(new HashMap<>());
			}
			
		}
		// s'il n'est pas connecté renvoyer erreur http 401:
		else {
			return ResponseEntity.status(401).body(null);
		}
		
		
		
	}
	
	

}
