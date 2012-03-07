// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.domain;

import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.domain.UserPreference;
import java.util.Set;

privileged aspect User_Roo_JavaBean {
    
    public String User.getUserName() {
        return this.userName;
    }
    
    public void User.setUserName(String userName) {
        this.userName = userName;
    }
    
    public String User.getFirstName() {
        return this.firstName;
    }
    
    public void User.setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String User.getLastName() {
        return this.lastName;
    }
    
    public void User.setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String User.getEmail() {
        return this.email;
    }
    
    public void User.setEmail(String email) {
        this.email = email;
    }
    
    public String User.getPassword() {
        return this.password;
    }
    
    public void User.setPassword(String password) {
        this.password = password;
    }
    
    public String User.getStatus() {
        return this.status;
    }
    
    public void User.setStatus(String status) {
        this.status = status;
    }
    
    public String User.getDescription() {
        return this.description;
    }
    
    public void User.setDescription(String description) {
        this.description = description;
    }
    
    public Double User.getAverageRating() {
        return this.averageRating;
    }
    
    public void User.setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    
    public String User.getType() {
        return this.type;
    }
    
    public void User.setType(String type) {
        this.type = type;
    }
    
    public Integer User.getAwardPoints() {
        return this.awardPoints;
    }
    
    public void User.setAwardPoints(Integer awardPoints) {
        this.awardPoints = awardPoints;
    }
    
    public String User.getAddressLine1() {
        return this.addressLine1;
    }
    
    public void User.setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    
    public String User.getAddressLine2() {
        return this.addressLine2;
    }
    
    public void User.setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    
    public String User.getCity() {
        return this.city;
    }
    
    public void User.setCity(String city) {
        this.city = city;
    }
    
    public String User.getZip() {
        return this.zip;
    }
    
    public void User.setZip(String zip) {
        this.zip = zip;
    }
    
    public String User.getCountry() {
        return this.country;
    }
    
    public void User.setCountry(String country) {
        this.country = country;
    }
    
    public String User.getHomePhone() {
        return this.homePhone;
    }
    
    public void User.setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }
    
    public String User.getMobile() {
        return this.mobile;
    }
    
    public void User.setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String User.getDisplayName() {
        return this.displayName;
    }
    
    public void User.setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public Set<PetDetail> User.getPets() {
        return this.pets;
    }
    
    public void User.setPets(Set<PetDetail> pets) {
        this.pets = pets;
    }
    
    public UserPreference User.getPreference() {
        return this.preference;
    }
    
    public void User.setPreference(UserPreference preference) {
        this.preference = preference;
    }
    
}