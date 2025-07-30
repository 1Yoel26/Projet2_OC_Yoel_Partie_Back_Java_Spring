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

import com.projet2_oc_y.projet2_oc_y.dto.UserDto;
import com.projet2_oc_y.projet2_oc_y.dto.UserDtoCreationCompte;
import com.projet2_oc_y.projet2_oc_y.model.Rentals;
import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.repository.UsersRepository;

import lombok.Data;

@Data
@Service
public class UsersService implements UserDetailsService {
	
	@Autowired
	private UsersRepository userRepo;
	
	
	public UserDto retourneUserConnecte(String email) {
		
		Users userConnecte =  userRepo.findByEmail(email);
			
		// convertion en UserDto:
		
		UserDto userDto = new UserDto();
		
		userDto.setId(userConnecte.getId());
		userDto.setName(userConnecte.getName());
		userDto.setEmail(userConnecte.getEmail());
		userDto.setCreatedAt(userConnecte.getCreatedAt());
		userDto.setUpdatedAt(userConnecte.getUpdatedAt());
		
		return userDto;
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
	
	
	public void insertionCompteBdd(UserDtoCreationCompte infoDucompte) {
		
		// encodage du mot de passe :
		String mdpEncoder;
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		mdpEncoder = infoDucompte.getPassword();
		
		mdpEncoder = bCryptPasswordEncoder.encode(mdpEncoder);
		
		// Enregistrement du Users complet :
		
		Users infoDuCompteComplet = new Users();
		
		infoDuCompteComplet.setEmail(infoDucompte.getEmail());
		infoDuCompteComplet.setName(infoDucompte.getName());
		
		infoDuCompteComplet.setPassword(mdpEncoder);
		
		infoDuCompteComplet.setCreatedAt(LocalDate.now());
		infoDuCompteComplet.setUpdatedAt(LocalDate.now());
		
	
		this.userRepo.save(infoDuCompteComplet);
		
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
	
	
	public UserDto afficherUnUser(int idUser) {
		
		Users unUser = userRepo.findById(idUser);
		
		UserDto unUserDto = new UserDto();
		
		unUserDto.setId(unUser.getId());
		unUserDto.setName(unUser.getName());
		unUserDto.setEmail(unUser.getEmail());
		unUserDto.setCreatedAt(unUser.getCreatedAt());
		unUserDto.setUpdatedAt(unUser.getUpdatedAt());
		
		return unUserDto;
		
	}

	
	
}
