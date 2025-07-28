package com.projet2_oc_y.projet2_oc_y.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.repository.UsersRepository;

import lombok.Data;

@Data
@Service
public class UsersService implements UserDetailsService {
	
	@Autowired
	private UsersRepository userRepo;
	
	
	public Users retourneUserConnecte(String email) {
		
		return userRepo.findByEmail(email);
		
	}
	
	// fonction pour savoir si le mail existe deja en Bdd avant de creer ce nouveau compte
	public boolean verificationEmailUnique(String email){
		
		Users testSiMailExiste = userRepo.findByEmail(email);
		
		// si aucun mail trouvé, le mail est dispo a true
		if(testSiMailExiste == null) {
			
			return true;
			
		}
		// sinon si un mail est trouvé:
		return false;
		
	}
	
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	public void insertionCompteBdd(Users infoDucompte) {
		
		String mdpEncoder;
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		mdpEncoder = infoDucompte.getPassword();
		
		mdpEncoder = bCryptPasswordEncoder.encode(mdpEncoder);
		
		infoDucompte.setPassword(mdpEncoder);
		
		infoDucompte.setCreatedAt(LocalDate.now());
		infoDucompte.setUpdatedAt(LocalDate.now());
		
		this.userRepo.save(infoDucompte);
		
	}
	
	
	// fonction pour récuperer l'id et le mdp de la bdd pour cet id,
	// puis creation d'un User comprehensible par Spring Security
	@Override
	public UserDetails loadUserByUsername(String identifiant) throws UsernameNotFoundException {

		Users userDeLABdd = userRepo.findByEmail(identifiant);
		
		// retourne l'user qui tente de se connecter dans un 
		// format compréhensible par SPring Security
		return new User(
				userDeLABdd.getEmail(),
				userDeLABdd.getPassword(),
				getGrantedAuthority("USER") // car ya pas de role Admin pour le moment
				);
		
	}
	
	
	private List<GrantedAuthority> getGrantedAuthority(String roleAajouter){
		
		List<GrantedAuthority> listeRoles = new ArrayList <GrantedAuthority>();
		
		listeRoles.add(new SimpleGrantedAuthority("ROLE_" + roleAajouter));
		
		return listeRoles;
		
		
	}
	
	
}
