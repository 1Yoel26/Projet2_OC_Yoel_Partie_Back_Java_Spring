package com.projet2_oc_y.projet2_oc_y.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "MessageDto", description = "DTO représentant un message")
@Data
public class MessageDto {
	
	@JsonProperty("rental_id")
	private int rentalId;
	
	// cet user_id va etre rempli dans le controlleur automatiquement via l'user connecté:
	@JsonProperty("user_id")
	private int userId;

	private String message;
}
