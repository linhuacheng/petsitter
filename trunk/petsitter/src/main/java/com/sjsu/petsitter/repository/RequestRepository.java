package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.Request;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Request.class)
public interface RequestRepository {

    List<com.sjsu.petsitter.domain.Request> findAll();
}
