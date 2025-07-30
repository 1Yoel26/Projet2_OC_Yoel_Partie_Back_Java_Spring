package com.projet2_oc_y.projet2_oc_y.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet2_oc_y.projet2_oc_y.dto.MessageDto;
import com.projet2_oc_y.projet2_oc_y.model.Messages;
import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.repository.MessagesRepository;
import com.projet2_oc_y.projet2_oc_y.repository.UsersRepository;

import lombok.Data;

@Data
@Service
public class MessagesService {
	
	@Autowired
	private MessagesRepository messageRepo;
	
	@Autowired
	private UsersRepository userRepo;
	
	
	public Users infoUserConnecte(String email) {

		return userRepo.findByEmail(email);
	}
	
	
	public void insertionMessage(MessageDto infoDuMessage) {
		
		
		// creation du message complet:
		Messages infoDuMessageComplet = new Messages();
		
		infoDuMessageComplet.setRentalId(infoDuMessage.getRentalId());
		infoDuMessageComplet.setUserId(infoDuMessage.getUserId());
		infoDuMessageComplet.setMessage(infoDuMessage.getMessage());
		
		infoDuMessageComplet.setCreatedAt(LocalDate.now());
		infoDuMessageComplet.setUpdatedAt(LocalDate.now());
		
		messageRepo.save(infoDuMessageComplet);
	}
	
	
	// validation du message
	public boolean validationMessage(MessageDto infoMessages) {
		
		// si le message contient quelques choses, on valide le message :
		if(infoMessages.getMessage().length() > 0) {
			return true;
		}
		
		return false;
		
	}
	
	
	
	
}
