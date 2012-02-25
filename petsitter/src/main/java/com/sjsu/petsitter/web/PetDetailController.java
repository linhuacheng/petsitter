package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.PetDetail;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/petdetails")
@Controller
@RooWebScaffold(path = "petdetails", formBackingObject = PetDetail.class)
public class PetDetailController {
}
