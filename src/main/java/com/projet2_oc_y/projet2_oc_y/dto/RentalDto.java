package com.projet2_oc_y.projet2_oc_y.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Data;


@Schema(name = "RentalDto", description = "DTO représentant une location")
@Data
public class RentalDto {

	private int id;
	
	private String name;
	
	private BigDecimal surface;
	
	private BigDecimal price;
	
	private MultipartFile picture;
	
	private String description;
	
	@JsonProperty("owner_id") 
	private int ownerId;
	
}
