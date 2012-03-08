package com.sjsu.petsitter.web;

import com.sjsu.petsitter.bean.PetswithUserPrinciple;
import com.sjsu.petsitter.domain.Feedback;
import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.service.RequestService;
import com.sjsu.petsitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RequestMapping("/feedbacks")
@Controller
@RooWebScaffold(path = "feedbacks", formBackingObject = Feedback.class)
public class FeedbackController {


    private List<Integer> ratingArray = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel, @RequestParam(value = "requestId", required = true) String requestId, HttpServletRequest httpRequest) {

        //populateEditForm(uiModel, new Feedback());
        if (requestId == null) {
            return "feedbacks/create";
        }
        PetswithUserPrinciple principal = getLogonUser();
        User feedbackToUser = feedbackService.getFeedbackToUser(requestId, principal);
        Feedback feedback = new Feedback();
        feedback.setFromUserId(principal.getUserId().toString());
        feedback.setFromUserName(principal.getUsername());
        feedback.setRequestId(requestId.toString());
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("feedback", feedback);
        uiModel.addAttribute("ratingValues", ratingArray);
        uiModel.addAttribute("feedbackToUser", feedbackToUser);
        return "feedbacks/create";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Feedback feedback, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, feedback);
            return "feedbacks/create";
        }
        uiModel.asMap().clear();
        PetswithUserPrinciple petswithUserPrinciple = getLogonUser();
        feedback.setFromUserName(petswithUserPrinciple.getUsername());
        User feedbackToUser = feedbackService.getFeedbackToUser(feedback.getRequestId(), petswithUserPrinciple);

        feedbackService.saveFeedbackToUser(feedbackToUser, feedback);
        //feedbackService.saveFeedback(feedback);
        return "redirect:/feedbacks/" + encodeUrlPathSegment(feedback.getFeedbackId().toString(), httpServletRequest);
    }


    @RequestMapping(value = "/{feedbackId}", produces = "text/html")
    public String show(@PathVariable("feedbackId") String feedbackId, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("feedback", feedbackService.getFeedbackByFeedbackId(feedbackId));
        uiModel.addAttribute("itemId", feedbackId);
        return "feedbacks/show";
    }

    private PetswithUserPrinciple getLogonUser() {
        PetswithUserPrinciple principal = (PetswithUserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return principal;
    }
}
