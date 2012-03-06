// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.service.UserService;
import com.sjsu.petsitter.web.UserController;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect RegistrationController_Roo_Controller {
    
    @Autowired
    UserService RegistrationController.userService;
    
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String RegistrationController.create(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, user);
            return "registration/create";
        }
        uiModel.asMap().clear();
        userService.saveUser(user);
        return "registration/success";
//        return "redirect:/users/" + encodeUrlPathSegment(user.getId().toString(), httpServletRequest);

//        return "users/create";
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String RegistrationController.createForm(Model uiModel) {
        populateEditForm(uiModel, new User());
        return "registration/create";
    }
    
    void RegistrationController.populateEditForm(Model uiModel, User user) {
        uiModel.addAttribute("user", user);
    }
  
    String RegistrationController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}