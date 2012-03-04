package com.sjsu.petsitter.service;


import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.domain.UserPreference;

public class DataInitializationService {
	
    @Autowired
    UserService userService;

	public void initialize() throws Exception {
	    User user = new User();
	    
	    user.setUserName("petowner1");
	    user.setFirstName("User 1");
	    user.setLastName("PetOwner");
	
	    user.setEmail("petowner1@petsittercom");
	    user.setPassword("petowner1");
	    user.setDescription("pet owner 1 description");
	
	    user.setAddressLine1("1234 Kiely Blvd");
	    user.setCity("Santa Clara");
	    user.setZip("95051");
	
	
	    user.setHomePhone("123-22-34");
	    user.setMobile("223-11-34");
	
	    user.setDisplayName("DisplayPetOwner1");
	    
	    PetDetail pet1 = new PetDetail();
	    
	    pet1.setPetName("bruce");
	    pet1.setPetType("Dog");
	    pet1.setDescription("bruce is cute");
	
	    PetDetail pet2 = new PetDetail();
	    
	    pet2.setPetName("John");
	    pet2.setPetType("Cat");
	    pet2.setDescription("John likes to meow");
	    
	    Set<PetDetail> user1Pets = new HashSet<PetDetail>();
	    user1Pets.add(pet1);
	    user1Pets.add(pet2);
	    
	    user.setPets(user1Pets);
	    
	    UserPreference userPref = new UserPreference();
	    userPref.setServiceLocation("Home");

	    userPref.setPetType("Dog");
	    userPref.setComment("user 1 preference");
	    
	    user.setPreference(userPref);

	    User user2 = new User();
	    
	    user2.setUserName("petowner2");
	    user2.setFirstName("User 2");
	    user2.setLastName("PetOwner");

	    user2.setEmail("petowner2@petsittercom");
	    user2.setPassword("petowner2");
	    user2.setDescription("pet owner 2 description");

	    user2.setAddressLine1("1234 Central Park");
	    user2.setCity("Santa Clara");
	    user2.setZip("95051");


	    user2.setHomePhone("123-22-34");
	    user2.setMobile("223-11-34");

	    user2.setDisplayName("DisplayPetOwner2");
	    
	    PetDetail pet3 = new PetDetail();
	    
	    pet3.setPetName("Mike");
	    pet3.setPetType("Dog");
	    pet3.setDescription("Mike is cute");

	    PetDetail pet4= new PetDetail();
	    
	    pet4.setPetName("Dave");
	    pet4.setPetType("Cat");
	    pet4.setDescription("Dave likes to meow");
	    
	    Set<PetDetail> user2Pets = new HashSet<PetDetail>();
	    user2Pets.add(pet3);
	    user2Pets.add(pet4);
	    
	    user2.setPets(user2Pets);
	    
	    UserPreference user2Pref = new UserPreference();
	    user2Pref.setServiceLocation("Home");

	    user2Pref.setPetType("Dog");
	    user2Pref.setComment("user 2 preference");
	    
	    user2.setPreference(user2Pref);
	    
	    userService.saveUser(user);
	    userService.saveUser(user2);

	}
	
}