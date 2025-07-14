package com.projet2_oc_y.projet2_oc_y.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.service.UsersService;


@RestController
public class UsersController {
	
	@Autowired
	private UsersService userService;
	
	@GetMapping("/users")
	public void getAllUsers(){
		
		Iterable<Users> lesUsers = userService.getAllUsersTest();
		
		for (Users unUser : lesUsers) {
			
			System.out.println(unUser.getEmail());
			
		}
	}
	
	
	
	@PostMapping("/insererUser")
	public void inser(@RequestBody Users unUser) {
		
		userService.insertionUserEnBddTest(unUser);
		
	}

}
