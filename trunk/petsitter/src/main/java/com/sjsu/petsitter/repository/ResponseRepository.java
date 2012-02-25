package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.Response;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Response.class)
public interface ResponseRepository {

    List<com.sjsu.petsitter.domain.Response> findAll();
}
