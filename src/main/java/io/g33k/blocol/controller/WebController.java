package io.g33k.blocol.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class WebController {
	private static final Logger log = LoggerFactory
			.getLogger(WebController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String showHomePage(Model model) {
		log.info("Returning home page");
		return "home";
	}
}
