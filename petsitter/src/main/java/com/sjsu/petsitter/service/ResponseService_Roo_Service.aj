// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.service;

import com.sjsu.petsitter.domain.Response;
import com.sjsu.petsitter.service.ResponseService;
import java.math.BigInteger;
import java.util.List;

privileged aspect ResponseService_Roo_Service {
    
    public abstract long ResponseService.countAllResponses();    
    public abstract void ResponseService.deleteResponse(Response response);    
    public abstract Response ResponseService.findResponse(BigInteger id);    
    public abstract List<Response> ResponseService.findAllResponses();    
    public abstract List<Response> ResponseService.findResponseEntries(int firstResult, int maxResults);    
    public abstract void ResponseService.saveResponse(Response response);    
    public abstract Response ResponseService.updateResponse(Response response);    
}
