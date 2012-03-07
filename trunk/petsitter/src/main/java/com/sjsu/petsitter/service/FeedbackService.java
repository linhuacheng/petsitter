package com.sjsu.petsitter.service;

import com.sjsu.petsitter.bean.PetswithUserPrinciple;
import com.sjsu.petsitter.domain.Feedback;
import com.sjsu.petsitter.domain.User;
import org.springframework.roo.addon.layers.service.RooService;

import java.math.BigInteger;


@RooService(domainTypes = {com.sjsu.petsitter.domain.Feedback.class})
public interface FeedbackService {

    public void saveFeedbackToUser(User feedbackToUser, Feedback feedback);

    public User getFeedbackToUser(String requestId, PetswithUserPrinciple principle);

    public Feedback getFeedbackByFeedbackId(String feedbackId);
}
