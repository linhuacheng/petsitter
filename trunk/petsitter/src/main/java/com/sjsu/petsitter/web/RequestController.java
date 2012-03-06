package com.sjsu.petsitter.web;

import javax.servlet.http.HttpServletRequest;

import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.Address;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.sjsu.petsitter.service.UserService;
import com.sjsu.petsitter.domain.User;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import com.sjsu.petsitter.util.RequestStatus;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/requests")
@Controller
@RooWebScaffold(path = "requests", formBackingObject = Request.class)
public class RequestController {
	
    @Autowired
    UserService userService;
    
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel, HttpServletRequest httpServletRequest) {
    	
    	// approverId
    	String strApproverId = httpServletRequest.getParameter("approverId");
    	
    	if (strApproverId == null || strApproverId.length() ==0) {
    		return "requests/create";
    	}
    	
    	BigInteger approverId = new BigInteger(strApproverId);
    	
    	User approver = userService.findUser(approverId);
    	Address approverAddress =  getAddress(approver);
    	
    	
    	String requestorUserName = getLogonUsername();
    	User requestor = userService.findUserByUserName(requestorUserName);
    	Address requestorAddress = getAddress(requestor);
    	
    	Request request = new Request();
    	request.setStatus(RequestStatus.NEW);
    	request.setRequestorId(requestor.getId().toString());
    	request.setRequestorUserName(requestor.getUserName());
    	request.setApproverUserName(approver.getUserName());
    	request.setApproverId(approver.getId().toString());
    	
        uiModel.addAttribute("request", request);
        uiModel.addAttribute("requestorPets", requestor.getPets());
        uiModel.addAttribute("approverName", approver.getDisplayName());
        uiModel.addAttribute("approverPets", approver.getPets());
        uiModel.addAttribute("requestorAddress", requestorAddress);
        uiModel.addAttribute("approverAddress", approverAddress);
        addDateTimeFormatPatterns(uiModel);
        
        return "requests/create";
    }
     
    private Address getAddress (User user) {
    	
    	Address address = new Address();
    	address.setLine1(user.getAddressLine1());
    	address.setLine2(user.getAddressLine2());
    	address.setCity(user.getCity());
    	address.setZipCode(user.getZip());
    	address.setHomePhone(user.getHomePhone());
    	address.setMobile(user.getMobile());
    	return address;
    }
    
    
	public static String getLogonUsername() {	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();

		//return username;
		return "petowner1";
	}
	
	 @RequestMapping(method = RequestMethod.POST, produces = "text/html")
	 public String create(@Valid Request request, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
	        if (bindingResult.hasErrors()) {
	            populateEditForm(uiModel, request);
	            return "requests/create";
	        }
	        uiModel.asMap().clear();
	        requestService.saveRequest(request);
	        return "redirect:/requests/" + encodeUrlPathSegment(request.getId().toString(), httpServletRequest);
	    }

}
