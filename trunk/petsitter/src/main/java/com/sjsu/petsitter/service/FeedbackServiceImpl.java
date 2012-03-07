package com.sjsu.petsitter.service;

import com.sjsu.petsitter.bean.PetswithUserPrinciple;
import com.sjsu.petsitter.domain.Feedback;
import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 3/7/12
 * Time: 5:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    UserService userService;

    @Autowired
    RequestService requestService;

    @Autowired
    MongoTemplate mongoTemplate;
    /**
     * saves feedback to user document
     *
     * @param feedbackToUser
     * @param feedback
     */
    public void saveFeedbackToUser(User feedbackToUser, Feedback feedback){

        Set<Feedback> feedbackSet = feedbackToUser.getFeedbackSet();

        if (feedbackSet == null){
            feedbackSet = new HashSet<Feedback>();
            feedbackToUser.setFeedbackSet(feedbackSet);
        }
        feedback.setFeedbackId(UUID.randomUUID().toString());
        feedbackSet.add(feedback);
        feedback.setCreatedDate(new Date());
        feedback.setUpdatedDate(new Date());
        feedback.setFeedbackDate(new Date());

        userService.saveUser(feedbackToUser);

    }

    /**
     * gets feedback to user document
     * @param requestId
     * @param principle
     * @return
     */
    public User getFeedbackToUser(String requestId, PetswithUserPrinciple principle) {
        Request request = requestService.findRequest(new BigInteger(requestId));

        BigInteger approverId = new BigInteger(request.getApproverId());
        BigInteger requesterId = new BigInteger(request.getRequestorId());
        User feedbackToUser = null;
        if (principle.getUserId().equals(approverId)) {
            feedbackToUser = userService.findUser(requesterId);
        } else {
            feedbackToUser = userService.findUser(approverId);
        }
        return feedbackToUser;
    }

    public Feedback getFeedbackByFeedbackId(String feedbackId){
        Query query = new Query(Criteria.where("feedbackSet.feedbackId").is(feedbackId));

        User user = mongoTemplate.findOne(query, User.class);
        if (!CollectionUtils.isEmpty(user.getFeedbackSet()))
        {
            for (Feedback feedback: user.getFeedbackSet()) {
                if (feedback.getFeedbackId().equalsIgnoreCase(feedbackId)){
                    return feedback;
                }
            }
        }
        return null;
    }
}
