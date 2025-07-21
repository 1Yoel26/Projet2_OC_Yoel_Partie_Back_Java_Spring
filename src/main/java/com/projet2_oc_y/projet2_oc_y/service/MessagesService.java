package com.projet2_oc_y.projet2_oc_y.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	public void insertionMessage(Messages infoDuMessage) {
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
	
	
	
	public String getEmail(int id_user) {
		
		String email_user = "";
		
		Optional<Users> infoUser = userRepo.findById(id_user);
		
		if(infoUser.isPresent()) {
			email_user = infoUser.get().getEmail();
		}
		
		return email_user;
		
		
	}

}
