package com.sjsu.petsitter.service;


import com.google.code.geocoder.model.LatLng;
import com.sjsu.petsitter.bean.SearchRequestBean;
import com.sjsu.petsitter.domain.AddressLoc;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.repository.UserRepository;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.geo.Point;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserServiceImpl implements UserService {

    public static final String PROP_USER_NAME = "userName";
    public static final String PROP_PREFERENCE_PET_TYPE = "pets.petType";
    public static final String PROP_ZIP = "zip";
    public static final String PROP_CITY = "city";
    public static final String PROP_PASSWORD = "password";
    public static final double RADIUS = (30.0/69.0);
    //1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA
    public static final Double[] Amphitheatre_1600_Pkwy_Mountain_View_CA_94043 = new Double[]{-122.08530320,37.42114440};
    //"160 N Main St, Milpitas, CA 95035, USA
    public static final Double[] NMainSt_160_Milpitas_CA_95035 = new Double[]{-121.9068760, 37.4327710};
    private static final Log log = LogFactory.getLog(UserServiceImpl.class);

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
        //searchRequestBean.setNearLoc(Amphitheatre_1600_Pkwy_Mountain_View_CA_94043);
        if (searchRequestBean.getNearLoc() != null && searchRequestBean.getNearLoc().length > 0
                && searchRequestBean.getNearLoc()[0] != null &&
                searchRequestBean.getNearLoc()[1] != null){
            log.debug("RADIUS:"+RADIUS);
            log.debug("Near Location lon"+searchRequestBean.getNearLoc()[0]);
            log.debug("Near Location lat"+ searchRequestBean.getNearLoc()[1]);
            Circle circle = new Circle(searchRequestBean.getNearLoc()[0], searchRequestBean.getNearLoc()[1], RADIUS);
            //Point point = new Point(searchRequestBean.getNearLoc()[0], searchRequestBean.getNearLoc()[1]);
            query = new Query(Criteria.where("loc").within(circle).and("id").ne(searchRequestBean.getLoggedOnUserId()))
                    .limit(maxResults).skip(firstResult);
        }
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

       updateLocationInformation(user);
    	
        userRepository.save(user);
    }

    public User updateUser(User user) {

        updateLocationInformation(user);
        return userRepository.save(user);
    }

    private void updateLocationInformation(User user){
        IndexDefinition indexDef = new GeospatialIndex("loc");
        mongoTemplate.ensureIndex(indexDef, User.class);
        String address = user.getAddressLine1() + " " + user.getAddressLine2() + ", " +user.getCity() + ", " +user.getState() + ", " + user.getCountry();
        LatLng location  = null;
        try {
    	    location = GeocodingService.getLocation(address);
        } catch (Exception e){
            log.error("Error in getting geocoding information address" + address, e);
        }

    	if (location != null) {
    		Double[] loc = new Double[2];
    		loc [AddressLoc.LONGITUDE_IDX] = location.getLng().doubleValue();
    		loc [AddressLoc.LATITUDE_IDX] = location.getLat().doubleValue();

    		user.setLoc(loc);
    	}
    }
}
