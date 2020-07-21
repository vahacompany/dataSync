package com.vaha.dataSync.app.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WelcomeController {
	Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@PostMapping("/welcomeResponse")
	public ResponseEntity<?> welcome(HttpServletRequest request) {
		
		logger.debug("welcome controller");
		request.getParameter("param");
		String rtnMsg = request.getParameter("param");

		return ResponseEntity.status(HttpStatus.OK).body(rtnMsg);
	}
	
	@PostMapping("/welcome")
	public String welcome(String name, Model model) {
		
		logger.debug("welcome controller");
		
		model.addAttribute("name", "홍길동");
		model.addAttribute("age", 20);
		
		return "welcome";
	}
}
