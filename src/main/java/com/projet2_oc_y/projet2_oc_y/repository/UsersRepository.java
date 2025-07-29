package com.projet2_oc_y.projet2_oc_y.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet2_oc_y.projet2_oc_y.model.Users;


@Repository
public interface UsersRepository extends CrudRepository<Users, Integer>{
	
	Users findByEmail(String emailAVerifier);
	
	Users findById(int idUser);
	
}
