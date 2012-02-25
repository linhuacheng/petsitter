package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.Response;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/responses")
@Controller
@RooWebScaffold(path = "responses", formBackingObject = Response.class)
public class ResponseController {
}
