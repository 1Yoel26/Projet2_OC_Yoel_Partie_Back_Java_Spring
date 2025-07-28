package com.projet2_oc_y.projet2_oc_y.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet2_oc_y.projet2_oc_y.model.Rentals;
import com.projet2_oc_y.projet2_oc_y.repository.RentalsRepository;

import lombok.Data;

@Data
@Service
public class RentalsService {
	
	@Autowired
	private RentalsRepository rentalRepo;
	
	public Iterable<Rentals> afficherLesRentals() {
		
		return rentalRepo.findAll();
		
	}
	
	public Optional<Rentals> afficherUnRental(int idDuRental) {
		
		return rentalRepo.findById(idDuRental);
		
	}

}
