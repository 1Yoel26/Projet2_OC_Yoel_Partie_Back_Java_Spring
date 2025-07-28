package com.projet2_oc_y.projet2_oc_y.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
	
	
	public void insertionMessage(Messages infoDuMessage) {
		
		
		infoDuMessage.setCreatedAt(LocalDate.now());
		infoDuMessage.setUpdatedAt(LocalDate.now());
		
		messageRepo.save(infoDuMessage);
	}
	
	
	// validation du message
	public boolean validationMessage(Messages infoMessages) {
		
		boolean MessageValide = true;
		
		if(MessageValide) {
			return true;
		}
		
		return false;
		
	}
	
	
	
	
}
