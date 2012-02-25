package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.Review;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reviews")
@Controller
@RooWebScaffold(path = "reviews", formBackingObject = Review.class)
public class ReviewController {
}
