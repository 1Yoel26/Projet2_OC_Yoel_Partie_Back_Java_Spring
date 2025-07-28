package com.projet2_oc_y.projet2_oc_y.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet2_oc_y.projet2_oc_y.model.Rentals;

@Repository
public interface RentalsRepository extends CrudRepository<Rentals, Integer>{

	Rentals findById(int idRental);
	
	Rentals findByOwnerId(int idUser);
}
