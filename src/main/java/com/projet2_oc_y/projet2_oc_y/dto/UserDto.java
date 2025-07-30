package com.projet2_oc_y.projet2_oc_y.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {
	
	private int id;
	
	private String email;
	
	private String name;
	
	@JsonProperty("created_at") 
	private LocalDate createdAt;
	
	@JsonProperty("updated_at")
	private LocalDate updatedAt;
	

}
