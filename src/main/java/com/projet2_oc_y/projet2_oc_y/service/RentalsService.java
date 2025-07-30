package com.projet2_oc_y.projet2_oc_y.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet2_oc_y.projet2_oc_y.dto.RentalDto;
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
	
	public void insertionRental(RentalDto infoDuRental) {
		
		// création du Rentals complet:
		
		Rentals infoDuRentalComplet = new Rentals();
		
		infoDuRentalComplet.setName(infoDuRental.getName());
		infoDuRentalComplet.setSurface(infoDuRental.getSurface());
		infoDuRentalComplet.setPrice(infoDuRental.getPrice());
		infoDuRentalComplet.setPicture(infoDuRental.getPicture());
		infoDuRentalComplet.setDescription(infoDuRental.getDescription());
		infoDuRentalComplet.setOwnerId(infoDuRental.getOwnerId());
		
		infoDuRentalComplet.setCreatedAt(LocalDate.now());
		infoDuRentalComplet.setUpdatedAt(LocalDate.now());
		
		rentalRepo.save(infoDuRentalComplet);
	}

	
	public boolean modificationRental(int idDuRentalAModifier, int idDuUser, RentalDto nouvelleInfoDuRental) {
		
		Rentals rentalTrouveParIdRentalEtIdUser = rentalRepo.findByIdAndOwnerId(idDuRentalAModifier, idDuUser);
		
		
		// si le rental existe pour cet id, et qu'il appartient bien a l'user connecté actuellement:
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
