// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.domain;

import com.sjsu.petsitter.domain.Review;
import java.math.BigInteger;
import java.util.Date;

privileged aspect Review_Roo_JavaBean {
    
    public BigInteger Review.getFromUserId() {
        return this.fromUserId;
    }
    
    public void Review.setFromUserId(BigInteger fromUserId) {
        this.fromUserId = fromUserId;
    }
    
    public BigInteger Review.getToUserId() {
        return this.toUserId;
    }
    
    public void Review.setToUserId(BigInteger toUserId) {
        this.toUserId = toUserId;
    }
    
    public String Review.getType() {
        return this.type;
    }
    
    public void Review.setType(String type) {
        this.type = type;
    }
    
    public Integer Review.getRating() {
        return this.rating;
    }
    
    public void Review.setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String Review.getComment() {
        return this.comment;
    }
    
    public void Review.setComment(String comment) {
        this.comment = comment;
    }
    
    public BigInteger Review.getRequestId() {
        return this.requestId;
    }
    
    public void Review.setRequestId(BigInteger requestId) {
        this.requestId = requestId;
    }
    
    public Date Review.getReviewDate() {
        return this.reviewDate;
    }
    
    public void Review.setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
    
    public Date Review.getCreatedDate() {
        return this.createdDate;
    }
    
    public void Review.setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    public Date Review.getUpdatedDate() {
        return this.updatedDate;
    }
    
    public void Review.setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    
}
