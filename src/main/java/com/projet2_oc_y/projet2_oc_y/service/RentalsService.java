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
		
		return rentalRepo.findAll();
		
	}
	
	
	public Optional<Rentals> afficherUnRental(int idDuRental) {
		
		return rentalRepo.findById(idDuRental);
		
	}
	
	
	public void insertionRental(RentalDto infoDuRental, MultipartFile imageDuRental) {
		
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

	
	public boolean modificationRental(int idDuRentalAModifier, int idDuUser, RentalDto nouvelleInfoDuRental, MultipartFile nouvelleImageDuRental) {
		
		// récupération des anciennes données de ce Rental avec l'id de l'url:
		Rentals rentalTrouveParIdRentalEtIdUser = rentalRepo.findByIdAndOwnerId(idDuRentalAModifier, idDuUser);
		
		
		// si le rental existe pour cet id, et qu'il appartient bien a l'user connecté actuellement:
		if(rentalTrouveParIdRentalEtIdUser != null) {
			
			// récuperation de l'ancienne image du rentals pour savoir si celle-ci à été modifier ou pas :
			String ancienneImage = rentalTrouveParIdRentalEtIdUser.getPicture();
			
			String nouvelleImage = nouvelleImageDuRental.getOriginalFilename();
			
			// si l'image à été modifier, suppression de l'ancienne et téléchargement de la nouvelle image:
			if(!nouvelleImage.isEmpty() && nouvelleImage != ancienneImage) {
				
				// affectation de la nouvelle image l'objet nouvelleInfoDuRental pour la modification quelques lignes plus bas:
				nouvelleInfoDuRental.setPicture(nouvelleImage);
				
				// Creation d'un objet TelechargementImage, pour utiliser dedans la fonction qui supprimera l'ancienne image et telechargera la nouvelle image sur le serveur:				TelechargementImage telechargerImage = new TelechargementImage();
				TelechargementImage modifierImage = new TelechargementImage();
				
				// suppression de l'ancienne image:
				modifierImage.suppressionImageSurLeServeur(ancienneImage);
				
				// telechargement de la nouvelle image:
				modifierImage.telechargementImageSurLeServeur(nouvelleImageDuRental);
				
				
			}else {
				nouvelleInfoDuRental.setPicture(ancienneImage);
			}
			
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
			
			System.out.println("Erreur, l'id du rental n'existe pas, ou n'appartient pas à cet utilisateur.");
			return false;
		}
		
		
	}
}
