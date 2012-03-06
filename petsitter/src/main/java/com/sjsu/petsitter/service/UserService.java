package com.sjsu.petsitter.service;

import org.springframework.roo.addon.layers.service.RooService;
import com.sjsu.petsitter.bean.SearchRequestBean;
import com.sjsu.petsitter.domain.User;


import java.util.List;

@RooService(domainTypes = { com.sjsu.petsitter.domain.User.class })
public interface UserService {

    public List<User> findPetOwners(SearchRequestBean searchRequestBean,int firstResult, int maxResults);

    User findUserByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);
}
