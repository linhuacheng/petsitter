package com.sjsu.petsitter.service;


import com.sjsu.petsitter.bean.SearchRequestBean;
import com.sjsu.petsitter.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> findPetOwners(SearchRequestBean searchRequestBean,int firstResult, int maxResults){
        Query query = null;
        if (StringUtils.isNotBlank(searchRequestBean.getPetType())){

            query = new Query(Criteria.where("preference.petType").regex(searchRequestBean.getPetType(), "i")).limit(maxResults).skip(firstResult);

//            return userRepository.findPetOwnerByPetType(searchRequestBean.getPetType()
//                    , new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults));
        } else if (StringUtils.isNotBlank(searchRequestBean.getZip())){

            query = new Query(Criteria.where("zip").is(searchRequestBean.getZip())).limit(maxResults).skip(firstResult);
//            return userRepository.findPetOwnerByZipCode(searchRequestBean.getZip()
//                    , new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults));
        } else if (StringUtils.isNotBlank(searchRequestBean.getCity())){
            query = new Query(Criteria.where("city").regex(searchRequestBean.getCity(),"i")).limit(maxResults).skip(firstResult);

//            return userRepository.findPetOwnerByCity(searchRequestBean.getCity()
//                    , new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults));
        }
        return query != null? mongoTemplate.find(query, User.class): null;
        //return mongoTemplate.find(query, User.class);
    }
}
