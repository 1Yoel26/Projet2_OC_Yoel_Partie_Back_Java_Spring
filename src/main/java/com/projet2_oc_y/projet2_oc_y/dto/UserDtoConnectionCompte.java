package com.projet2_oc_y.projet2_oc_y.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "UserDtoConnectionCompte", description = "DTO spécialiser uniquement pour l'authentification (avec le password)")
@Data
public class UserDtoConnectionCompte {
	
	private String email;
	
	private String password;
	

}
