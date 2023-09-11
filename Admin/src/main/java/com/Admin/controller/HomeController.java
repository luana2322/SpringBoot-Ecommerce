package com.Admin.controller;

import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
	
	@GetMapping("/index")
	public String index(ModelMap modelMap) {
		return "index";
	}
}
