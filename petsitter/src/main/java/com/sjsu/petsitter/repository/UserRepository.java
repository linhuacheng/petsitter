package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.User;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.Query;


@RooMongoRepository(domainType = User.class)
public interface UserRepository{

    List<com.sjsu.petsitter.domain.User> findAll();

    @Query(value="{\"preference.petType\": ?0}")
    List<User> findPetOwnerByPetType(String petType, PageRequest pageRequest);

    @Query(value="{zip: ?0}")
    List<User> findPetOwnerByZipCode(String zipCode, PageRequest pageRequest);

    @Query(value="{\"city\": {\"$regex\": ?0, $options: \"i\"}}")
    List<User> findPetOwnerByCity(String city, PageRequest pageRequest);

}
