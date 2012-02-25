package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.UserPreference;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/userpreferences")
@Controller
@RooWebScaffold(path = "userpreferences", formBackingObject = UserPreference.class)
public class UserPreferenceController {
}
