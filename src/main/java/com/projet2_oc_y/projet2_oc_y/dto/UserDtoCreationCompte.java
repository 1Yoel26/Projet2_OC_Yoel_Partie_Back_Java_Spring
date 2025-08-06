package com.projet2_oc_y.projet2_oc_y.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "UserDtoCreationCompte", description = "DTO spécialiser uniquement pour la création d'un compte utilisateur (avec l'id, le password, et les dates en plus)")
@Data
public class UserDtoCreationCompte {
	
private int id;
	
	private String email;
	
	private String name;
	
	private String password;
	
	@JsonProperty("created_at") 
	private LocalDate createdAt;
	
	@JsonProperty("updated_at")
	private LocalDate updatedAt;
	

}
