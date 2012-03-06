package com.sjsu.petsitter.service;

import org.springframework.roo.addon.layers.service.RooService;

import com.sjsu.petsitter.domain.PetDetail;

@RooService(domainTypes = { com.sjsu.petsitter.domain.PetDetail.class })
public interface PetDetailService {
	
    public PetDetail findPetDetailByPetId(String petId);
}
