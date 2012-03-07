package com.sjsu.petsitter.service;

import org.springframework.roo.addon.layers.service.RooService;
import com.sjsu.petsitter.domain.Request;
import java.util.List;
import java.math.BigInteger;;

@RooService(domainTypes = { com.sjsu.petsitter.domain.Request.class })
public interface RequestService {
    
    public abstract long countAllRequests();    
    public abstract void deleteRequest(Request request);    
    public abstract Request findRequest(BigInteger id);    
    public abstract List<Request> findAllRequests();    
    public abstract List<Request> findRequestEntries(int firstResult, int maxResults);    
    public abstract void saveRequest(Request request);    
    public abstract Request updateRequest(Request request);    
    
    public abstract List<Request>findAllMyRequests(String userName);   
    public abstract List<Request> findMyRequestEntries(String userName, int firstResult, int maxResults);    
}
