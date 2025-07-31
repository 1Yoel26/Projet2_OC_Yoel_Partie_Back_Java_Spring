package com.projet2_oc_y.projet2_oc_y.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.dto.UserDto;
import com.projet2_oc_y.projet2_oc_y.dto.UserDtoConnectionCompte;
import com.projet2_oc_y.projet2_oc_y.dto.UserDtoCreationCompte;
import com.projet2_oc_y.projet2_oc_y.model.Rentals;
import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.service.JwtService;
import com.projet2_oc_y.projet2_oc_y.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Users", description = "Opérations liées aux comptes utilisateurs : connection, inscription ...")
@RestController
public class UsersController {
	
	@Autowired
	private UsersService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Operation(summary = "Enregistrement d'un nouveau compte utilisateur.", description = "Enregistrement d'un nouveau compte utilisateur après avoir vérifié que l'email de ce nouveau compte n'est pas déjà pris par un autre compte.")
	@PostMapping("/auth/register")
	public ResponseEntity<?> reponseHttpCreationCompte(@RequestBody UserDtoCreationCompte infoCreationCompte){
		
		String id = infoCreationCompte.getEmail();
		
		
		// si le mail est déjà pris en Bdd, retourn erreur:
		if(!userService.verificationEmailUnique(id)) {
		
			System.out.println("Cet email est déjà pris.");
			
			return ResponseEntity.badRequest().body(new HashMap<>());
		}
		
		// si le mail est bien libre, sauvegarde du compte en bdd 
		// puis retourné le token :
		
		
		userService.insertionCompteBdd(infoCreationCompte);
		
		String tokenJwt = jwtService.genererLeTokenPourCreationCompte(id);
		
		HashMap<String, String> body = new HashMap<>();
		
		body.put("token", tokenJwt);
		
		return ResponseEntity.ok(body);
	}
	
	
	@Operation(summary = "Authentification à son compte utilisateur.", description = "Après avoir validé les infos d'authentification, génération d'un Token JWT afin de pouvoir accéder à toute les routes sécurisés dans l'application.")
	@PostMapping("/auth/login")
	public ResponseEntity<?>reponseHttpConnectionCompte(@RequestBody UserDtoConnectionCompte infoConnectioCompte){
		
		// tentative de connection
		try {
			
			Authentication authentication = authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(
		                infoConnectioCompte.getEmail(),
		                infoConnectioCompte.getPassword()
		            )
		        );
			
			String tokenJwt = jwtService.genererLeTokenPourConnectionCompte(authentication);
			
			
			
			HashMap<String, String> body = new HashMap<>();
	        body.put("token", tokenJwt);
	        
	        return ResponseEntity.ok(body);
			
		} 
		
		// si id ou mdp incorrect
		catch (Exception e) {
			
			HashMap<String, String> body = new HashMap<>();
	        body.put("message", "error");
	        
	        return ResponseEntity.status(401).body(body);
	        
		}
		
		
		
	} // fin de la route
	
	
	@Operation(summary = "Retourne les infos du compte utilisateur.", description = "Retourne les infos du compte utilisateur qui est connecté actuellement uniquement.")
	@GetMapping("/auth/me")
	public ResponseEntity<?> retourneUserConnecte(Authentication authentication) {
		
			// si l'user n'est pas connecté:
			if(authentication == null) {
			
				return ResponseEntity.status(401).body(new HashMap<>());
			}
			
			
			// sinon, s'il est bien connecté
			String email = authentication.getName();
			
			UserDto userConnecte = userService.retourneUserConnecte(email);
			
			// structure la réponse à retourner:
		    Map<String, Object> body = new LinkedHashMap<>();
		    
		    body.put("id", userConnecte.getId());
		    body.put("name", userConnecte.getName());
		    body.put("email", userConnecte.getEmail());
		    body.put("created_at", userConnecte.getCreatedAt());
		    body.put("updated_at", userConnecte.getUpdatedAt());

		    return ResponseEntity.ok(body);
		
	}
	
	
	@Operation(summary = "Retourne les infos d'un des comptes utilisateur.", description = "Retourne les infos de n'importe quel compte utilisateur existant.")
	@GetMapping("/user/{idDuUser}")
	public ResponseEntity<?> afficheUnUser(@PathVariable int idDuUser) {
		
		UserDto unUser = userService.afficherUnUser(idDuUser);
		
		return ResponseEntity.ok(unUser);
	}
	
	
	
	
	

}
