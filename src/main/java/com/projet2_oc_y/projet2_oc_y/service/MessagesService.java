package com.projet2_oc_y.projet2_oc_y.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet2_oc_y.projet2_oc_y.repository.MessagesRepository;

import lombok.Data;

@Data
@Service
public class MessagesService {
	
	@Autowired
	private MessagesRepository messageRepo;

}
