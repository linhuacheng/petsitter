package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.Review;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Review.class)
public interface ReviewRepository {

    List<com.sjsu.petsitter.domain.Review> findAll();
}
