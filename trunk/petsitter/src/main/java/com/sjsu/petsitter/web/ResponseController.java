package com.sjsu.petsitter.web;

import javax.servlet.http.HttpServletRequest;

import com.sjsu.petsitter.domain.Response;
import com.sjsu.petsitter.domain.Request;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import com.sjsu.petsitter.service.RequestService;
import com.sjsu.petsitter.util.RequestStatus;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/responses")
@Controller
@RooWebScaffold(path = "responses", formBackingObject = Response.class)
public class ResponseController {
	
	
    @Autowired
    RequestService requestService;
    
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel, HttpServletRequest httpServletRequest) {
    	
       	String strRequestId = httpServletRequest.getParameter("requestId");
       	Request request = requestService.findRequest(new BigInteger(strRequestId));
       	Response response = new Response();
       	response.setRequestId(strRequestId);
       	
       	boolean isRequestor = request.getRequestorUserName().equalsIgnoreCase(getLogonUsername());
       	boolean isApprover = request.getApproverUserName().equalsIgnoreCase(getLogonUsername());
       	
        uiModel.addAttribute("response", response);
        uiModel.addAttribute("request", request);
        uiModel.addAttribute("isRequestor", isRequestor);
        
        boolean canReject = false, canConfirm =false, canRespond =false, canAccept=false, canCancel=false,canClose=false;
        String requestStatus = request.getStatus();
        if (RequestStatus.NEW.equals(requestStatus)) {
        	if (isRequestor) {
        		canRespond=true;
        		canCancel=true;
        	} else if (isApprover) {
        		canRespond=true;
        		canAccept=true;
        		canReject=true;
        	}
        } else if (RequestStatus.ACCEPTED.equals(requestStatus)) {
        	if (isRequestor) {
        		canRespond=true;
        		canConfirm=true;
        		canCancel=true;
        	} else if (isApprover) {
        		canRespond =true;
        	}
        } else if (RequestStatus.CONFIRMED.equals(requestStatus)) {
        	if (isRequestor) {
        		canClose=true;
        		canRespond=true;
        	} else if (isApprover) {
        		canRespond=true;
        	}
        } else if (RequestStatus.REJECTED.equals(requestStatus)) {
        	if (isRequestor) {
        		canClose=true;
        	}
        }
   
        uiModel.addAttribute("canRespond", canRespond);
        uiModel.addAttribute("canAccept", canAccept);
        uiModel.addAttribute("canConfirm", canConfirm);
        uiModel.addAttribute("canReject", canReject);
        uiModel.addAttribute("canCancel", canCancel);
        uiModel.addAttribute("canClose", canClose);
          
        
        addDateTimeFormatPatterns(uiModel);
        
        return "responses/create";
    }
    
	public static String getLogonUsername() {	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

		return username;
	}
	
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Response response, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, response);
            return "responses/create";
        }
        
        String strRequestId = response.getRequestId();
       	Request request = requestService.findRequest(new BigInteger(strRequestId));
       	String requestStatus = response.getStatus();
       	
       	if (requestStatus!= null && requestStatus.length()>0) {
       		request.setStatus(requestStatus);
       	}
       	response.setRespondent(getLogonUsername());
       	response.setResponseDate(new Date());
       	response.setCreatedDate(new Date());
       	response.setUpdatedDate(new Date());
        
       	List<Response> responses = request.getReponses();
       	if (responses == null ) {
       		responses = new ArrayList<Response>();
       	}
       	responses.add(response);      
       	request.setReponses(responses);
       			
        uiModel.asMap().clear();
        requestService.saveRequest(request);
        return "redirect:/requests/" + encodeUrlPathSegment(request.getId().toString(), httpServletRequest);
    }
    
}
