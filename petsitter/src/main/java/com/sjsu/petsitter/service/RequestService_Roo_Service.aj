// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.service;

import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.service.RequestService;
import java.math.BigInteger;
import java.util.List;

privileged aspect RequestService_Roo_Service {
    
    public abstract long RequestService.countAllRequests();    
    public abstract void RequestService.deleteRequest(Request request);    
    public abstract Request RequestService.findRequest(BigInteger id);    
    public abstract List<Request> RequestService.findAllRequests();    
    public abstract List<Request> RequestService.findRequestEntries(int firstResult, int maxResults);    
    public abstract void RequestService.saveRequest(Request request);    
    public abstract Request RequestService.updateRequest(Request request);    
}