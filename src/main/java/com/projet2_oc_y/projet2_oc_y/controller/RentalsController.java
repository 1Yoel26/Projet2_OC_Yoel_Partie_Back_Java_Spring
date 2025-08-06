package com.projet2_oc_y.projet2_oc_y.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.projet2_oc_y.projet2_oc_y.dto.RentalDto;
import com.projet2_oc_y.projet2_oc_y.dto.UserDto;
import com.projet2_oc_y.projet2_oc_y.model.Rentals;
import com.projet2_oc_y.projet2_oc_y.model.Users;
import com.projet2_oc_y.projet2_oc_y.service.RentalsService;
import com.projet2_oc_y.projet2_oc_y.service.UsersService;
import com.projet2_oc_y.projet2_oc_y.service.util.TelechargementImage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Rentals", description = "Opérations liées aux locations : création, modification ...")
@RestController
public class RentalsController {
	
	@Autowired
	private RentalsService rentalService;
	
	
	@Autowired
	private UsersService userService;
	
	@Operation(summary = "Enregistrement d'une location.", description = "Insertion en Bdd de la location (Rental) en ajoutant automatiquement certaines infos comme son id_user, sa date de création et de modification.")
	@PostMapping(value = "/api/rentals", consumes = {"multipart/form-data"})
	public ResponseEntity<?> insertionRental(
			@ModelAttribute RentalDto infoDuRental,
			Authentication authentication){
		
		// récupération de l'user connecté:
		UserDto unUser = userService.retourneUserConnecte(authentication.getName());
		
		int idOwnerRental = unUser.getId();
		
		infoDuRental.setOwnerId(idOwnerRental);
		
		
		// insertion du Rental en Bdd:
		rentalService.insertionRental(infoDuRental);
		
		
		// retourne le message de retour:
		HashMap<String, String> body = new HashMap<>();
        body.put("message", "Rental created !");
        
		return ResponseEntity.ok(body);
		
	}
	
	@Operation(summary = "Modification d'une location.", description = "Modification d'une location via son id dans l'url, après avoir validé que cet id de rental existe, et qu'il appartient bien à l'utilisateur connecté.")
	@PutMapping(value = "/api/rentals/{idDuRental}", consumes = {"multipart/form-data"})
	public ResponseEntity<?> modificationRental( 
			@ModelAttribute RentalDto infoDuRental,
			@PathVariable int idDuRental, 
			Authentication authentication){
		
		UserDto unUser = userService.retourneUserConnecte(authentication.getName());
		
		int idUserConnecte = unUser.getId();
		
		
		boolean modifFaite = rentalService.modificationRental(idDuRental, idUserConnecte, infoDuRental);
		
		
		if(modifFaite) {
			HashMap<String, String> body = new HashMap<>();
	        body.put("message", "Rental updated !");
	        
	        return ResponseEntity.ok(body);
	        
		}else {
		
			return ResponseEntity.badRequest().body("Erreur d'id, ou données incorrect.");
		}
		
	} // fin de la route
	
	@Operation(summary = "Retourne toutes les locations.", description = "Retourne toutes les locations de la Bdd.")
	@GetMapping("/api/rentals")
	public ResponseEntity<?> lesRentals() {
		
		List<Rentals> listeRentals = (List<Rentals>) rentalService.afficherLesRentals();
		
		Map<String, List<Rentals>> body = new HashMap<>();
		body.put("rentals", listeRentals);
		
		return ResponseEntity.ok(body);
	}
	
	
	@Operation(summary = "Retourne une location.", description = "Retourne une location via son id_rental.")
	@GetMapping("/api/rentals/{idDuRental}")
	public ResponseEntity<?> unRental(@PathVariable int idDuRental) {
		
		Optional<Rentals> unRental = rentalService.afficherUnRental(idDuRental);
		
		return ResponseEntity.ok(unRental);
	}
	
	
	
	
}
