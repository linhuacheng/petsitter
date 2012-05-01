package com.sjsu.petsitter.domain;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@RooJavaBean
@RooToString
@RooMongoEntity
@XmlRootElement(name="user")
public class User {

    @NotNull
    private String userName;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String status;

    private String description;

    private Double averageRating;

    private String type;

    private Integer awardPoints;

    @Size(max = 255)
    private String addressLine1;

    @Size(max = 255)
    private String addressLine2;

    @Size(max = 100)
    private String city;

    @Size(max = 50)
    private String zip;
    
    @Size(max = 50)
    private String state;

    @Size(max = 100)
    private String country;

    @Size(max = 100)
    private String homePhone;

    @Size(max = 100)
    private String mobile;

    @Size(max = 255)
    private String displayName;
    
    private Set<PetDetail> pets;

    private Set<Feedback> feedbackSet;
    
    private UserPreference preference;

    private Double[] loc;
    
	public Double[] getLoc() {
		return loc;
	}

	public void setLoc(Double[] loc) {
		this.loc = loc;
	}
}
