package com.Customer.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
	
	@GetMapping("/login")
	public String login(ModelMap modelMap) {
		modelMap.addAttribute("tiltle","Login Page");
		return "login";
	}
}
