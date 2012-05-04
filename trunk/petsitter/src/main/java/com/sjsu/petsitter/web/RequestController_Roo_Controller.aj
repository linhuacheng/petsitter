// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.service.AddressService;
import com.sjsu.petsitter.service.PetDetailService;
import com.sjsu.petsitter.service.RequestService;
import com.sjsu.petsitter.service.ResponseService;
import com.sjsu.petsitter.web.RequestController;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect RequestController_Roo_Controller {
    
    @Autowired
    RequestService RequestController.requestService;
    
    @Autowired
    AddressService RequestController.addressService;
    
    @Autowired
    PetDetailService RequestController.petDetailService;
    
    @Autowired
    ResponseService RequestController.responseService;
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String RequestController.show(@PathVariable("id") BigInteger id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("request", requestService.findRequest(id));
        uiModel.addAttribute("itemId", id);
        return "requests/show";
    }
    
    
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String RequestController.updateForm(@PathVariable("id") BigInteger id, Model uiModel) {
        populateEditForm(uiModel, requestService.findRequest(id));
        return "requests/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String RequestController.delete(@PathVariable("id") BigInteger id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Request request = requestService.findRequest(id);
        requestService.deleteRequest(request);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/requests";
    }
    
    void RequestController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("request_requeststartdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("request_requestenddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("request_switchstartdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("request_switchenddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("request_createddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("request_updateddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void RequestController.populateEditForm(Model uiModel, Request request) {
        uiModel.addAttribute("request", request);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("addresses", addressService.findAllAddresses());
        uiModel.addAttribute("petdetails", petDetailService.findAllPetDetails());
        uiModel.addAttribute("responses", responseService.findAllResponses());
    }
    
    String RequestController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
