package com.sjsu.petsitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
}
