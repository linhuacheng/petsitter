package com.sjsu.petsitter.service;


import com.sjsu.petsitter.service.RequestService;
import java.math.BigInteger;
import java.util.List;
import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.repository.RequestRepository;
import com.sjsu.petsitter.service.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.OrQuery;

public class RequestServiceImpl implements RequestService {
	
    
    @Autowired
    RequestRepository requestRepository;
    
	  @Autowired
	  MongoTemplate mongoTemplate;
    
    public long countAllRequests() {
        return requestRepository.count();
    }
    
    public void deleteRequest(Request request) {
        requestRepository.delete(request);
    }
    
    public Request findRequest(BigInteger id) {
        return requestRepository.findOne(id);
    }
    
    public List<Request> findAllRequests() {
        return requestRepository.findAll();
    }
    
    public List<Request> findRequestEntries(int firstResult, int maxResults) {
        return requestRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }
    
    public void saveRequest(Request request) {
        requestRepository.save(request);
    }
    
    public Request updateRequest(Request request) {
        return requestRepository.save(request);
    }
    
    public List<Request> findMyRequestEntries(String userName, int firstResult, int maxResults) {
    	//Query query=  new OrQuery(new Query(Criteria.where("requestorUserName").is(userName)) , new Query(Criteria.where("approverUserName").is(userName))).limit(maxResults).skip(firstResult);

    	Query query=  new Query(new Criteria().orOperator(Criteria.where("requestorUserName").is(userName),Criteria.where("approverUserName").is(userName))).limit(maxResults).skip(firstResult);

    	return mongoTemplate.find(query, Request.class);
       }
    
    public List<Request> findAllMyRequests(String userName) {
    	Query query=   new Query(new Criteria().orOperator(Criteria.where("requestorUserName").is(userName),Criteria.where("approverUserName").is(userName)));

    	return mongoTemplate.find(query, Request.class);
    }
}
