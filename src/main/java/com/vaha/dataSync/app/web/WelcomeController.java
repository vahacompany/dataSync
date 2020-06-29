package com.vaha.dataSync.app.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@GetMapping("/welcome")
	public String welcome(String name, Model model) {
		
		logger.debug("welcome controller");
		
		model.addAttribute("name", "홍길동");
		model.addAttribute("age", 20);
		
		return "welcome";
	}
}
