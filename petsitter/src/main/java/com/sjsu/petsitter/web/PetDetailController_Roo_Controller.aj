// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.service.PetDetailService;
import com.sjsu.petsitter.web.PetDetailController;
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

privileged aspect PetDetailController_Roo_Controller {
    
    @Autowired
    PetDetailService PetDetailController.petDetailService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String PetDetailController.create(@Valid PetDetail petDetail, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, petDetail);
            return "petdetails/create";
        }
        uiModel.asMap().clear();
        petDetailService.savePetDetailToUser(petDetail);
        return "redirect:/petdetails/" + encodeUrlPathSegment(petDetail.getPetId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String PetDetailController.createForm(Model uiModel) {
        populateEditForm(uiModel, new PetDetail());
        return "petdetails/create";
    }
    
    @RequestMapping(value = "/{petId}", produces = "text/html")
    public String PetDetailController.show(@PathVariable("petId") String petId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("petdetail", petDetailService.findPetDetailByPetId(petId));
        uiModel.addAttribute("itemId", petId);
        return "petdetails/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String PetDetailController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        uiModel.addAttribute("petdetails", petDetailService.findPetDetailsByUser());
        addDateTimeFormatPatterns(uiModel);
        return "petdetails/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String PetDetailController.update(@Valid PetDetail petDetail, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, petDetail);
            return "petdetails/update";
        }
        uiModel.asMap().clear();
        petDetailService.updatePetIdByUser(petDetail);
        return "redirect:/petdetails/" + encodeUrlPathSegment(petDetail.getPetId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{petId}", params = "form", produces = "text/html")
    public String PetDetailController.updateForm(@PathVariable("petId") String petId, Model uiModel) {
        populateEditForm(uiModel, petDetailService.findPetDetailByPetId(petId));
        return "petdetails/update";
    }
    
    @RequestMapping(value = "/{petId}", method = RequestMethod.DELETE, produces = "text/html")
    public String PetDetailController.delete(@PathVariable("petId") String petId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        petDetailService.deletePetDetailByUser(petId);
        uiModel.asMap().clear();
//        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
//        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/petdetails";
    }
    
    void PetDetailController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("petDetail_createddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("petDetail_updateddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void PetDetailController.populateEditForm(Model uiModel, PetDetail petDetail) {
        uiModel.addAttribute("petDetail", petDetail);
        addDateTimeFormatPatterns(uiModel);
    }
    
    String PetDetailController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
