// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.domain;

import com.sjsu.petsitter.domain.UserPreference;
import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

privileged aspect UserPreference_Roo_Mongo_Entity {
    
    declare @type: UserPreference: @Persistent;
    
    @Id
    private BigInteger UserPreference.id;
    
    public BigInteger UserPreference.getId() {
        return this.id;
    }
    
    public void UserPreference.setId(BigInteger id) {
        this.id = id;
    }
    
}
