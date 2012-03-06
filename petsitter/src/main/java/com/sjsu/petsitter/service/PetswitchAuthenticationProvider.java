package com.sjsu.petsitter.service;

import com.sjsu.petsitter.bean.PetswithUserPrinciple;
import com.sjsu.petsitter.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * petwitch authentication provider
 * User: ckempaiah
 * Date: 3/6/12
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class PetswitchAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    UserService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        User user = null;
        String password = (String) authenticationToken.getCredentials();
        if (!StringUtils.hasText(password)) {
            throw new BadCredentialsException("Please enter password");
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        try {
            user = userService.findByUserNameAndPassword(userName, password);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } catch (EmptyResultDataAccessException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (EntityNotFoundException e) {
            throw new BadCredentialsException("Invalid user");
        } catch (NonUniqueResultException e) {
            throw new BadCredentialsException("Non-unique user, contact administrator");
        }
        return new PetswithUserPrinciple(user.getId(), userName, password, true, // enabled
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                authorities);

    }
}
