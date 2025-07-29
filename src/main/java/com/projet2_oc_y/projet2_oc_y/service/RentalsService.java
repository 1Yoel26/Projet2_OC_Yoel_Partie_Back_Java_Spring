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
	
	
	public Optional<Rentals> afficherUnRental(int idDuRental) {
		
		return rentalRepo.findById(idDuRental);
		
	}
	
	public void insertionRental(Rentals infoDuRental) {
		
		rentalRepo.save(infoDuRental);
	}

	
	public boolean modificationRental(int idDuRentalAModifier, int idDuUser, Rentals nouvelleInfoDuRental) {
		
		Rentals rentalTrouveParIdRentalEtIdUser = rentalRepo.findByIdAndOwnerId(idDuRentalAModifier, idDuUser);
		
		
		// si le rental existe pour cet id, et qu'il appartient bien a l'user connecté :
		if(rentalTrouveParIdRentalEtIdUser != null) {
			
			rentalTrouveParIdRentalEtIdUser.setName(nouvelleInfoDuRental.getName());
			rentalTrouveParIdRentalEtIdUser.setSurface(nouvelleInfoDuRental.getSurface());
			rentalTrouveParIdRentalEtIdUser.setPrice(nouvelleInfoDuRental.getPrice());
			rentalTrouveParIdRentalEtIdUser.setPicture(nouvelleInfoDuRental.getPicture());
			rentalTrouveParIdRentalEtIdUser.setDescription(nouvelleInfoDuRental.getDescription());
			rentalTrouveParIdRentalEtIdUser.setUpdatedAt(LocalDate.now());
			
			rentalRepo.save(rentalTrouveParIdRentalEtIdUser);
			
			return true;
		}
		else {
			return false;
		}
		
		
	}
}
