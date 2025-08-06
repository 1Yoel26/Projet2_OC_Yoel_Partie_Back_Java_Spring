package com.projet2_oc_y.projet2_oc_y.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.projet2_oc_y.projet2_oc_y.dto.RentalDto;
import com.projet2_oc_y.projet2_oc_y.model.Rentals;
import com.projet2_oc_y.projet2_oc_y.repository.RentalsRepository;
import com.projet2_oc_y.projet2_oc_y.service.util.TelechargementImage;

import lombok.Data;

@Data
@Service
public class RentalsService {
	
	@Autowired
	private RentalsRepository rentalRepo;
	
	public Iterable<Rentals> afficherLesRentals() {
		
		Iterable<Rentals> lesRentals = rentalRepo.findAll();
		
		for(Rentals unRental : lesRentals) {
			
			if(unRental.getPicture() != null && !unRental.getPicture().isEmpty()) {
				unRental.setPicture("http://localhost:8080/Images/" + unRental.getPicture());
			}
			
		}
		
		return lesRentals;
		
	}
	
	
	public Optional<Rentals> afficherUnRental(int idDuRental) {
		
		Optional<Rentals> leRental = rentalRepo.findById(idDuRental);
		
		// si le rental existe ajoute le prefixe a l'image pour avoir l'url complete:
		leRental.ifPresent(leRent -> {
			if(leRent.getPicture() != null && !leRent.getPicture().isEmpty()) {
				leRent.setPicture("http://localhost:8080/Images/" + leRent.getPicture());
			}
		});
		
		return leRental;
	}
	
	
	public void insertionRental(RentalDto infoDuRental) {
		
		// récupération de l'image
		MultipartFile imageDuRental = infoDuRental.getPicture();
		
		String nomImageDuRental = "";
		
		// téléchargement de l'image:
		
		// sauvegarde de l'image sur le serveur:
		if(imageDuRental != null && !imageDuRental.isEmpty()) {
			
			// variable pour récupérer le nom de l'image:
			nomImageDuRental = imageDuRental.getOriginalFilename();
					
			// Creation d'un objet TelechargementImage, pour utiliser dedans la fonction qui telechargera l'image sur le serveur:				TelechargementImage telechargerImage = new TelechargementImage();
			TelechargementImage telechargerImage = new TelechargementImage();
			
			// appel de la fonction pour la télécharger et stock le statut du téléchargement dans la variable:
			String resultatTelechargement = telechargerImage.telechargementImageSurLeServeur(imageDuRental);
				
			// affiche le résultat du téléchargement dans la console:
			System.out.println(resultatTelechargement);
		}
				
		
		// création du Rentals complet:
		
		Rentals infoDuRentalComplet = new Rentals();
		
		infoDuRentalComplet.setName(infoDuRental.getName());
		infoDuRentalComplet.setSurface(infoDuRental.getSurface());
		infoDuRentalComplet.setPrice(infoDuRental.getPrice());
		
		// valeur de l'image = le nom de l'image selectioné, ou vide. (car l'image est facultative)
		infoDuRentalComplet.setPicture(nomImageDuRental);
		
		infoDuRentalComplet.setDescription(infoDuRental.getDescription());
		infoDuRentalComplet.setOwnerId(infoDuRental.getOwnerId());
		
		infoDuRentalComplet.setCreatedAt(LocalDate.now());
		infoDuRentalComplet.setUpdatedAt(LocalDate.now());
		
		rentalRepo.save(infoDuRentalComplet);
		
		
		System.out.println("image reçu : " + imageDuRental.getOriginalFilename());
		System.out.println("DTO reçu : " + infoDuRental);
	}

	
	public boolean modificationRental(int idDuRentalAModifier, int idDuUser, RentalDto nouvelleInfoDuRental) {
		
		// récupération des anciennes données de ce Rental avec l'id de l'url:
		Rentals rentalTrouveParIdRentalEtIdUser = rentalRepo.findByIdAndOwnerId(idDuRentalAModifier, idDuUser);
		
		
		// si le rental existe pour cet id, et qu'il appartient bien a l'user connecté actuellement:
		if(rentalTrouveParIdRentalEtIdUser != null) {
			
			// on garde le nom de l'ancienne image car elle ne peut pas être modifié dans l'app angular :
			String nomAncienneImage = rentalTrouveParIdRentalEtIdUser.getPicture();
			
			
			rentalTrouveParIdRentalEtIdUser.setName(nouvelleInfoDuRental.getName());
			rentalTrouveParIdRentalEtIdUser.setSurface(nouvelleInfoDuRental.getSurface());
			rentalTrouveParIdRentalEtIdUser.setPrice(nouvelleInfoDuRental.getPrice());
			rentalTrouveParIdRentalEtIdUser.setPicture(nomAncienneImage);
			rentalTrouveParIdRentalEtIdUser.setDescription(nouvelleInfoDuRental.getDescription());
			rentalTrouveParIdRentalEtIdUser.setUpdatedAt(LocalDate.now());
			
			rentalRepo.save(rentalTrouveParIdRentalEtIdUser);
			
			return true;
		}
		else {
			
			System.out.println("Erreur, l'id du rental n'existe pas, ou n'appartient pas à cet utilisateur.");
			return false;
		}
		
		
	}
}
