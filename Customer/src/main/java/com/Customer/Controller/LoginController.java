package com.Customer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.Core.Service.CustomerService;
import com.Core.Service.Impl.CustomerServiceIpl;
import com.Core.dto.CustomerDto;
import com.Core.model.Customer;
import com.Core.repository.CustomerRepository;
import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;

@Controller
public class LoginController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CustomerServiceIpl customerServiceIpl;


//	@GetMapping("/login")
//	public String login(ModelMap modelMap) {
//		modelMap.addAttribute("tiltle","Login Page");
//		return "login";
//	}
	
	@GetMapping("/register")
	public String register(ModelMap modelMap) {
		modelMap.addAttribute("tiltle","Register");
		modelMap.addAttribute("newcustomer",new CustomerDto());
		return "login";
	}
	
	@PostMapping("/register-new")
	public String registernew(@Valid CustomerDto customerDto,
									BindingResult bindingResult,
									ModelMap modelMap) {
		modelMap.addAttribute("tiltle","Login Page");
		if(bindingResult.hasErrors()) {
			System.out.println("eorr bindng");
			modelMap.addAttribute("eror", "Something went wrong!!!!");
			modelMap.addAttribute("newcustomer", new CustomerDto());
			return "login";
		}
		
		Customer customer= customerRepository.findByUsername(customerDto.getUsername()).isPresent()
				? customerRepository.findByUsername(customerDto.getUsername()).get()
				: null;
		
		if (customer != null) {
			System.out.println("custoemrexited");
			modelMap.addAttribute("newcustomer", new CustomerDto());
			modelMap.addAttribute("message", "Username existed!!");
			return "login";
		}
		
		if(!customerDto.getPassword().equals(customerDto.getConfirmpassword())) {
			modelMap.addAttribute("newcustomer", new CustomerDto());
			modelMap.addAttribute("message", "Password is not same!!");
			return "login";
		}
		
		customerServiceIpl.save(customerDto);
		return "login";
	}
	
	
}
