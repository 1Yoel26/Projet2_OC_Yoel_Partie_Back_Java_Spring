package com.projet2_oc_y.projet2_oc_y.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.model.Rentals;
import com.projet2_oc_y.projet2_oc_y.service.RentalsService;

@RestController
public class RentalsController {
	
	@Autowired
	private RentalsService rentalService;
	
	@GetMapping("api/rentals")
	public ResponseEntity<?> lesRentals() {
		
		List<Rentals> listeRentals = (List<Rentals>) rentalService.afficherLesRentals();
		
		Map<String, List<Rentals>> body = new HashMap<>();
		body.put("rentals", listeRentals);
		
		return ResponseEntity.ok(body);
	}
	
	

}
