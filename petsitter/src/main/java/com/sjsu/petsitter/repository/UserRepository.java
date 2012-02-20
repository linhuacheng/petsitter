package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.User;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = User.class)
public interface UserRepository {

    List<com.sjsu.petsitter.domain.User> findAll();
}
