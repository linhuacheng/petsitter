package com.sjsu.petsitter.service;


import com.google.code.geocoder.model.LatLng;
import com.sjsu.petsitter.bean.SearchRequestBean;
import com.sjsu.petsitter.domain.AddressLoc;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.repository.UserRepository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserServiceImpl implements UserService {

    public static final String PROP_USER_NAME = "userName";
    public static final String PROP_PREFERENCE_PET_TYPE = "pets.petType";
    public static final String PROP_ZIP = "zip";
    public static final String PROP_CITY = "city";
    public static final String PROP_PASSWORD = "password";
    @Autowired
    MongoTemplate mongoTemplate;
    
    @Autowired
    UserRepository userRepository;

    /**
     * finds user who are also pet owners by passed in search request bean
     * @param searchRequestBean
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<User> findPetOwners(SearchRequestBean searchRequestBean,int firstResult, int maxResults){
        Query query = null;
        if (StringUtils.isNotBlank(searchRequestBean.getPetType())){

            query = new Query(Criteria.where(PROP_PREFERENCE_PET_TYPE).regex(searchRequestBean.getPetType(), "i")
                    .and("id").ne(searchRequestBean.getLoggedOnUserId())).limit(maxResults).skip(firstResult);

        } else if (StringUtils.isNotBlank(searchRequestBean.getZip())){

            query = new Query(Criteria.where(PROP_ZIP).is(searchRequestBean.getZip())
                    .and("id").ne(searchRequestBean.getLoggedOnUserId())).limit(maxResults).skip(firstResult);
        } else if (StringUtils.isNotBlank(searchRequestBean.getCity())){
            query = new Query(Criteria.where(PROP_CITY).regex(searchRequestBean.getCity(),"i")
                    .and("id").ne(searchRequestBean.getLoggedOnUserId())).limit(maxResults).skip(firstResult);

        }
        return query != null? mongoTemplate.find(query, User.class): null;
     }

    /**
     * finds user by user name
     * @param userName
     * @return
     */
    public User findUserByUserName(String userName){
        Query query = new Query(Criteria.where(PROP_USER_NAME).is(userName));
        return mongoTemplate.findOne(query,User.class);
    }

    public User findByUserNameAndPassword(String userName, String password) {
        Query query = new Query(Criteria.where(PROP_USER_NAME).is(userName).andOperator(Criteria.where(PROP_PASSWORD).is(password)));
        return mongoTemplate.findOne(query,User.class);
    }
    
    public void saveUser(User user) {
    	String address = user.getAddressLine1() + " " + user.getAddressLine2() + ", " +user.getCity() + ", " +user.getState() + ", " + user.getCountry();
    	LatLng location = GeocodingService.getLocation(address); 
    	
    	if (location != null) {
    		AddressLoc loc = new AddressLoc();
    		loc.setLat(location.getLat());
    		loc.setLon(location.getLng());
    		
    		user.setAddressLoc(loc);
    	}
    	
        userRepository.save(user);
    }
}
