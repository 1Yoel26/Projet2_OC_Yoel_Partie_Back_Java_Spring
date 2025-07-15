package com.projet2_oc_y.projet2_oc_y.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.repository.UsersRepository;

import lombok.Data;

@Data
@Service
public class UsersService {
	
	@Autowired
	private UsersRepository userRepo;
	
	// fonction pour savoir si le mail existe deja en Bdd avant de creer ce nouveau compte
	public boolean verificationEmailUnique(Users infoDuUser){
		
		Optional<Users> testSiMailExiste = userRepo.findByEmail(infoDuUser.getEmail());
		
		if(testSiMailExiste.isPresent()) {
			
			return false;
			
		}
		
		// ajout des données sur les dates
		infoDuUser.setCreatedAt(LocalDate.now());
	    infoDuUser.setUpdatedAt(LocalDate.now());
		
		// insertion en Bdd du compte
		userRepo.save(infoDuUser);
		
		return true;
		
		
	}
	
	
	public boolean verificationEmailEtMdpCorrect(String identifiant, String mdp) {
		
		Optional<Users> testIdentifiantExiste = userRepo.findByEmail(identifiant);
		Optional<Users> testIdentifiantEtMdpCorrect = userRepo.findByEmailAndPassword(identifiant, mdp);
		
		
		// si id existe, recupere son mail en bdd
		if(testIdentifiantExiste.isPresent()) {
			
			String mdpDeLaBdd = testIdentifiantExiste.get().getPassword();
			
			if(mdpDeLaBdd.equals(mdp)) {
				// connecté
				return true;
			}else {
				// id existe, mais mdp incorrect
				return false;
			}
			
			
		}
		
		// sinon si id ou mdp incorrect
		return false;
	}
	
	
}
