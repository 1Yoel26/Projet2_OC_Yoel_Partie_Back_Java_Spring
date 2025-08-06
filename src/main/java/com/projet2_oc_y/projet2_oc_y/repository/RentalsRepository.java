package com.projet2_oc_y.projet2_oc_y.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet2_oc_y.projet2_oc_y.model.Rentals;

@Repository
public interface RentalsRepository extends CrudRepository<Rentals, Integer>{

	// fonction pour verifier si :
	// - Un rental existe bien pour l'id dans l'url
	// - Et si ce rental appartient bien à l'utilisateur connecté actuellement
	
	Rentals findByIdAndOwnerId(int idRental, int idUser);
	
}
