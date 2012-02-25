package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.UserPreference;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = UserPreference.class)
public interface UserPreferenceRepository {

    List<com.sjsu.petsitter.domain.UserPreference> findAll();
}
