package com.sjsu.petsitter.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

import com.sjsu.petsitter.domain.PetDetail;

@RooService(domainTypes = { com.sjsu.petsitter.domain.PetDetail.class })
public interface PetDetailService {
	
    public PetDetail findPetDetailByPetId(String petId);
    public void savePetDetailToUser(PetDetail petdetail);
    public void deletePetDetailByUser(String petId);
    public List<PetDetail> findPetDetailsByUser();
    public List<PetDetail> findPetDetailsByUserName(String userName);
    public void updatePetIdByUser(PetDetail petDetail);
}
