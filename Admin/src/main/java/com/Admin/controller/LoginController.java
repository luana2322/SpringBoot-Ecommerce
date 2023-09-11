package com.Admin.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Core.Service.Impl.UsersServiceImpl;
import com.Core.dto.UserDto;
import com.Core.model.Users;
import com.Core.repository.UsersRepository;

import jakarta.validation.Valid;

@Controller
public class LoginController {
	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersServiceImpl usersServiceImpl;

	@GetMapping("/login")
	public String login(ModelMap modelMap) {
		modelMap.addAttribute("title", "Login Page");
		modelMap.addAttribute("user", new Users());
		return "login";
	}

	@GetMapping("/register")
	public String register(ModelMap modelMap) {
		modelMap.addAttribute("title", "Register");
		modelMap.addAttribute("newdto", new UserDto());
		return "register";
	}

	@GetMapping("/forgot-password")
	public String forgotpassword(ModelMap modelMap) {
		modelMap.addAttribute("title", "Forgot Password!!");
		return "forgot-password";
	}

	@PostMapping("/register-new")
	public String registernew(@Valid UserDto userDto, BindingResult bindingResult, ModelMap modelMap) {

		if (bindingResult.hasErrors()) {
			modelMap.addAttribute("newdto", new UserDto());
			return "register";
		}

		try {
			modelMap.remove("message");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Users user = usersRepository.findByUsername(userDto.getUsername()).isPresent()
				? usersRepository.findByUsername(userDto.getUsername()).get()
				: null;

		if (user != null) {
			modelMap.addAttribute("newdto", new UserDto());
			modelMap.addAttribute("message", "Username existed!!");
			return "register";
		}

		if (!userDto.getPassword().equals(userDto.getRepeatpassword())) {
			modelMap.addAttribute("newdto", new UserDto());
			modelMap.addAttribute("message", "Password is not same!!");
			return "register";
		}

		usersServiceImpl.save(usersServiceImpl.maptouser(userDto));
		return "login";
	}

	@RequestMapping("/index")
	public String homepage(ModelMap modelMap) {
		modelMap.addAttribute("title", "Home Page");
		return "index";
	}

}
