package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.Feedback;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Feedback.class)
public interface FeedbackRepository {

    List<com.sjsu.petsitter.domain.Feedback> findAll();
}
