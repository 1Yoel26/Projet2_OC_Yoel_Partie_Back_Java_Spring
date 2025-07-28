package com.projet2_oc_y.projet2_oc_y.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String email;
	
	private String name;
	
	private String password;
	
	@JsonProperty("created_at")
	@Column(name = "created_at")
	private LocalDate createdAt;
	
	@JsonProperty("updated_at")
	@Column(name = "updated_at")
	private LocalDate updatedAt;
	
	
	

}
