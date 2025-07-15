package com.projet2_oc_y.projet2_oc_y.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.model.Messages;
import com.projet2_oc_y.projet2_oc_y.service.MessagesService;

@RestController
public class MessagesController {
	
	@Autowired
	private MessagesService messageService;
	
	public ResponseEntity<?> reponseHttpMessage(@RequestBody Messages infoDuMessage){
		
		boolean connecte = false;
		
		if(connecte) {
			
		}
		
		
		
	}
	
	

}
