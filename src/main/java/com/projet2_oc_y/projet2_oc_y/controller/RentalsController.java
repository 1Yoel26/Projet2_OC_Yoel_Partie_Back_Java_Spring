package com.projet2_oc_y.projet2_oc_y.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.projet2_oc_y.projet2_oc_y.service.RentalsService;

@RestController
public class RentalsController {
	
	@Autowired
	private RentalsService rentalService;
	
	

}
