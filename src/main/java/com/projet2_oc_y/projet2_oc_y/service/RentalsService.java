package com.projet2_oc_y.projet2_oc_y.service;

import java.time.LocalDate;
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
	
	
	public Rentals afficherUnRental(int idDuRental) {
		
		return rentalRepo.findById(idDuRental);
		
	}
	
	public void insertionRental(Rentals infoDuRental) {
		
		rentalRepo.save(infoDuRental);
	}

	
	public boolean modificationRental(int idDuRentalAModifier, int idDuUser, Rentals nouvelleInfoDuRental) {
		
		Rentals rentalTrouveParIdRental = rentalRepo.findById(idDuRentalAModifier);
		
		Rentals rentalTrouveParIdUserConnecte = rentalRepo.findByOwnerId(idDuUser);
		
		// si le rental existe pour cet id, et qu'il appartient bien a l'user connecté :
		if(rentalTrouveParIdRental != null &&  rentalTrouveParIdUserConnecte != null) {
			
			rentalTrouveParIdUserConnecte.setName(nouvelleInfoDuRental.getName());
			rentalTrouveParIdUserConnecte.setSurface(nouvelleInfoDuRental.getSurface());
			rentalTrouveParIdUserConnecte.setPrice(nouvelleInfoDuRental.getPrice());
			rentalTrouveParIdUserConnecte.setPicture(nouvelleInfoDuRental.getPicture());
			rentalTrouveParIdUserConnecte.setDescription(nouvelleInfoDuRental.getDescription());
			rentalTrouveParIdUserConnecte.setUpdatedAt(LocalDate.now());
			
			rentalRepo.save(rentalTrouveParIdUserConnecte);
			
			return true;
		}
		else {
			return false;
		}
		
		
	}
}
