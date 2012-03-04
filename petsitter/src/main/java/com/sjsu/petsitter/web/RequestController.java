package com.sjsu.petsitter.web;

import javax.servlet.http.HttpServletRequest;
import com.sjsu.petsitter.domain.Request;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.sjsu.petsitter.service.UserService;
import com.sjsu.petsitter.domain.User;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import com.sjsu.petsitter.util.RequestStatus;

@RequestMapping("/requests")
@Controller
@RooWebScaffold(path = "requests", formBackingObject = Request.class)
public class RequestController {
	
    @Autowired
    UserService userService;
    
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel, HttpServletRequest httpServletRequest) {
    	
    	// petSitterId
    	String strPetSitterId = httpServletRequest.getParameter("petSitterId");
    	
    	if (strPetSitterId == null || strPetSitterId.length() ==0) {
    		return "requests/create";
    	}
    	
    	BigInteger petSitterId = new BigInteger(strPetSitterId);
    	System.out.println("PetSitterId:" + petSitterId);
    	
    	User petSitter = userService.findUser(petSitterId);
    	
    	Request request = new Request();
    	request.setPetSitterId(petSitter.getId());
    	request.setStatus(RequestStatus.NEW);
    	
    	User myAccount = userService.findUser(new BigInteger("24549844967302108965912466479"));
    	
        uiModel.addAttribute("request", request);
        uiModel.addAttribute("myPets", myAccount.getPets());
        uiModel.addAttribute("petSitterName", petSitter.getDisplayName());
        uiModel.addAttribute("petSitterPets", petSitter.getPets());
        addDateTimeFormatPatterns(uiModel);
        
        return "requests/create";
    }
    
    
}
