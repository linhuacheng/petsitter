package com.sjsu.petsitter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.domain.User;


public class PetDetailServiceImpl implements PetDetailService {
	
	  @Autowired
	  MongoTemplate mongoTemplate;
	  
    public PetDetail findPetDetailByPetId(String petId) {
        Query query = new Query(Criteria.where("pets.petId").is(petId));

//      return userRepository.findPetOwnerByPetType(searchRequestBean.getPetType()
//              , new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults));
        User user = mongoTemplate.findOne(query, User.class);
        PetDetail pet = null;
        if (user != null && user.getPets()!=null) {
	        for (PetDetail petDetail: user.getPets()) {
	        	if (petDetail.getPetId().equals(petId)) {
	        		pet =  petDetail;
	        	}
	        }
        }
        return pet;
    }
    
    public PetDetail findPetDetailByPetType(String petType) {
    	
        Query query = new Query(Criteria.where("pets.petType").is(petType));

//      return userRepository.findPetOwnerByPetType(searchRequestBean.getPetType()
//              , new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults));
        User user = mongoTemplate.findOne(query, User.class);
        PetDetail pet = null;
        if (user != null && user.getPets()!=null) {
	        for (PetDetail petDetail: user.getPets()) {
	        	if (petDetail.getPetType().equals(petType)) {
	        		pet =  petDetail;
	        	}
	        }
        }
        return pet;
    }
    
public void savePetDetailToUser(PetDetail petdetail){
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		petdetail.setPetId(UUID.randomUUID().toString());
		petdetail.setCreatedDate(new Date());
		Set<PetDetail> pets = new HashSet<PetDetail>();
		Query query = new Query(Criteria.where("userName").is(username));
		User user = mongoTemplate.findOne(query, User.class);

		if(user.getPets()!=null)
		{
			pets = user.getPets();
			pets.add(petdetail);
		}
		else
			pets.add(petdetail);
		user.setPets(pets);
		mongoTemplate.save(user);
		
    }
    
    public List<PetDetail> findPetDetailsByUser(){
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		
		Set<PetDetail> pets = new HashSet<PetDetail>();
		Query query = new Query(Criteria.where("userName").is(username));
		User user = mongoTemplate.findOne(query, User.class);
		if(user.getPets()!=null)
		{
			pets = user.getPets();
			
		}
		
		List<PetDetail> list = new ArrayList<PetDetail>(pets);
		return list;
		
    }
    
    
    public List<PetDetail> findPetDetailsByUserName(String userName){
		
		Set<PetDetail> pets = new HashSet<PetDetail>();
		Query query = new Query(Criteria.where("userName").is(userName));
		User user = mongoTemplate.findOne(query, User.class);
		if(user.getPets()!=null)
		{
			pets = user.getPets();
			
		}
		
		List<PetDetail> list = new ArrayList<PetDetail>(pets);
		return list;
		
    }
    
    
    public void deletePetDetailByUser(String petId)
    {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Set<PetDetail> pets = new HashSet<PetDetail>();
		Query query = new Query(Criteria.where("userName").is(username));
		User user = mongoTemplate.findOne(query, User.class);
		if (user != null && user.getPets()!=null) {
			pets = user.getPets();
	        for (PetDetail petDetail: pets) {
	        	if (petDetail.getPetId().equals(petId)) {
	        		pets.remove(petDetail);
	        		break;
	        	}
	        }
	        user.setPets(pets);
	        mongoTemplate.save(user);
        }
		
    }
    
    public void updatePetIdByUser(PetDetail petdetail){
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		Set<PetDetail> pets = new HashSet<PetDetail>();
		String petId = petdetail.getPetId();
		petdetail.setUpdatedDate(new Date());
		PetDetail pet = new PetDetail();
		Query query = new Query(Criteria.where("userName").is(username));
		User user = mongoTemplate.findOne(query, User.class);
		if (user != null && user.getPets()!=null) {
			pets = user.getPets();
	        for (PetDetail petDetail: pets) {
	        	if (petDetail.getPetId().equals(petId)) {
	        		pets.remove(petDetail);	
	        		break;
	        	}
	        }
	        pets.add(petdetail);
	        user.setPets(pets);
	        mongoTemplate.save(user);
        }
    }

}
