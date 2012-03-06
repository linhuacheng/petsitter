package com.sjsu.petsitter.domain;

import java.util.Date;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooMongoEntity
public class Request {

    @Size(max = 50)
    private String requestType;

    @Size(max = 1000)
    private String comment;

    @Size(max = 100)
    private String status;

    private String requestorId;
    
    private String requestorUserName;
    
    private String approverId;
    
    private String approverUserName;
 
    private Address requestorAddress;
    
    private PetDetail requestorPet;

    private Address approverAddress;
    
    private PetDetail approverPet;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date requestStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date requestEndDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date updatedDate;
    
    private List<Response> reponses;
}
