package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.Request;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/requests")
@Controller
@RooWebScaffold(path = "requests", formBackingObject = Request.class)
public class RequestController {
}
