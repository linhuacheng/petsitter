package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.PetDetail;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = PetDetail.class)
public interface PetDetailRepository {

    List<com.sjsu.petsitter.domain.PetDetail> findAll();
}
