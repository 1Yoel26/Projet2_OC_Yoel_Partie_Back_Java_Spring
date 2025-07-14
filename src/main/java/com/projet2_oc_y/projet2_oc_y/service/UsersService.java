package com.projet2_oc_y.projet2_oc_y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.repository.UsersRepository;

import lombok.Data;

@Data
@Service
public class UsersService {
	
	@Autowired
	private UsersRepository userRepo;
	
	public void insertionUserEnBddTest(Users infoUser) {
		
		userRepo.save(infoUser);
		
	}
	
	public Iterable<Users> getAllUsersTest() {
		
		return userRepo.findAll();
	}

}
