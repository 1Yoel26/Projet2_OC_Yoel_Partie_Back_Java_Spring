package com.projet2_oc_y.projet2_oc_y.service.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;


public class TelechargementImage {
	
	
	public String telechargementImageSurLeServeur(MultipartFile imageATelecharger) {
		
		// si l'image et son nom existe alors on essaye de la telecharger:
		if(imageATelecharger != null && !imageATelecharger.isEmpty()) {
			try {
				
				// chemin de telechargement des imgs (a partir de la racine du projet):
				String cheminDeTelechargement = System.getProperty("user.dir") + File.separator +  "Images" + File.separator;
				
				// création du vrai chemin sur le serveur (s'il n'existe pas uniquement)
				Files.createDirectories(Paths.get(cheminDeTelechargement));
				
				// récupere le nom de l'image
				String nomImage = imageATelecharger.getOriginalFilename();
				
				Path cheminDeTelechargementComplet = Paths.get(cheminDeTelechargement + nomImage);
				
				
				// telechargement de l'img sur le serveur, dans ce chemin complet :
				imageATelecharger.transferTo(cheminDeTelechargementComplet.toFile());
				
				// si le upload à réussi ne rien afficher:
				return "";
				
			} 
			
			catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				return "System.out.println('Erreur, IOException lors du telechargement : ' + e.getMessage())";
			}
			
			catch (Exception e) {
				e.printStackTrace();
				
				System.out.println("Erreur, Exception lors du telechargement : " + e.getMessage());
				// si l'image existe, mais que le upload n'a pas réussi
				return "Erreur, l'image existe, mais l'upload n'a pas réussi."; 
			}
		}
		
		// si l'image n'existe même pas:
		return "Erreur, l'image n'existe pas.";
	}
	
	
	public String suppressionImageSurLeServeur(String nomImageASupprimer) {
		
		if(nomImageASupprimer != null && !nomImageASupprimer.isEmpty()) {
			try {
				
				// chemin de telechargement des imgs (a partir de la racine du projet):
				String cheminDuDossier = System.getProperty("user.dir") + File.separator +  "Images" + File.separator;
				
				// chemin de l'img a supprimer
				Path cheminImage = Paths.get(cheminDuDossier + nomImageASupprimer);
				
				if(Files.deleteIfExists(cheminImage)) {
					return "";
				}else {
					return "Erreur de la suppression de l'image, car l'image n'existe pas.";
				}
				
			} catch (Exception e) {
				
				return "Erreur lors de la suppression de l'image.";
			}
		}else {
			// si le nom de l'image à supprimer est vide ou null:
			return "Erreur de la suppression de l'image, car le nom de l'image est vide.";
		}
		
	}

}
