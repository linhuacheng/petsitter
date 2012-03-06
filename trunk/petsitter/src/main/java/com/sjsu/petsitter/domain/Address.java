package com.sjsu.petsitter.domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
public class Address {

    @NotNull
    private String line1;

    private String line2;

    @NotNull
    private String city;

    private String zipCode;

    @NotNull
    private String homePhone;

    @NotNull
    private String mobile;

}
