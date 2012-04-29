package com.sjsu.petsitter.bean;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;

/**
 * request bean for the search request
 * User: ckempaiah
 * Date: 3/4/12
 * Time: 12:41 AM
 * To change this template use File | Settings | File Templates.
 */
@RooJavaBean
@RooToString
@XmlRootElement(name="SearchRequestBean")
public class SearchRequestBean {

    private String petType;
    private String zip;
    private String city;
    private Integer page;
    private Integer size;
    private BigInteger loggedOnUserId;

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public BigInteger getLoggedOnUserId() {
        return loggedOnUserId;
    }

    public void setLoggedOnUserId(BigInteger loggedOnUserId) {
        this.loggedOnUserId = loggedOnUserId;
    }
}
