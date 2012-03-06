package com.sjsu.petsitter.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.repository.UserRepository;
import com.sjsu.petsitter.service.UserService;
import com.sjsu.petsitter.web.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/registration")
@Controller
@RooWebScaffold(path = "registration", formBackingObject = User.class)
public class RegistrationController {
    }

