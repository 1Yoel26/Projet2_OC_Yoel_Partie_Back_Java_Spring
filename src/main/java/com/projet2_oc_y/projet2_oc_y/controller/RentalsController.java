package com.projet2_oc_y.projet2_oc_y.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.model.Rentals;
import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.service.RentalsService;
import com.projet2_oc_y.projet2_oc_y.service.UsersService;

@RestController
public class RentalsController {
	
	@Autowired
	private RentalsService rentalService;
	
	
	@Autowired
	private UsersService userService;
	
	@GetMapping("api/rentals")
	public ResponseEntity<?> lesRentals() {
		
		List<Rentals> listeRentals = (List<Rentals>) rentalService.afficherLesRentals();
		
		Map<String, List<Rentals>> body = new HashMap<>();
		body.put("rentals", listeRentals);
		
		return ResponseEntity.ok(body);
	}
	
	
	@GetMapping("api/rentals/{idDuRental}")
	public ResponseEntity<?> unRental(@PathVariable int idDuRental) {
		
		Optional<Rentals> unRental = rentalService.afficherUnRental(idDuRental);
		
		return ResponseEntity.ok(unRental);
	}
	
	
	@PostMapping("api/rentals")
	public ResponseEntity<?> insertionRental(@RequestBody Rentals infoDuRental, Authentication authentication){
		
		Users unUser = userService.retourneUserConnecte(authentication.getName());
		
		int idOwnerRental = unUser.getId();
		
		infoDuRental.setOwnerId(idOwnerRental);
		
		rentalService.insertionRental(infoDuRental);
		
		HashMap<String, String> body = new HashMap<>();
        body.put("message", "Rental created !");
        
		
		return ResponseEntity.ok(body);
		
		
	}
	

	@PutMapping("api/rentals/{idDuRental}")
	public ResponseEntity<?> insertionRental(@RequestBody Rentals infoDuRental, @PathVariable int idDuRental, Authentication authentication){
		
		Users unUser = userService.retourneUserConnecte(authentication.getName());
		
		int idUserConnecte = unUser.getId();
		
		
		boolean modifFaite = rentalService.modificationRental(idDuRental, idUserConnecte, infoDuRental);
		
		if(modifFaite) {
			HashMap<String, String> body = new HashMap<>();
	        body.put("message", "Rental updated !");
	        
	        return ResponseEntity.ok(body);
	        
		}else {
		
			return ResponseEntity.badRequest().body("Erreur d'id, ou données incorrect.");
		}
		
		
		
		
		
	}
	
}
