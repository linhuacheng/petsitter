package com.sjsu.petsitter.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.math.BigInteger;
import java.util.Collection;

/**
 * user principle bean saved in security context
 * User: ckempaiah
 * Date: 3/6/12
 * Time: 12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class PetswithUserPrinciple extends User {

    private BigInteger userId;
    public PetswithUserPrinciple(BigInteger userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }
}
