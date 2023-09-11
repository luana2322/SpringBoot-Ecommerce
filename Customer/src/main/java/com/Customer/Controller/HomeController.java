package com.Customer.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.Core.Service.ProductService;
import com.Core.dto.ProductDto;
import com.Core.model.Customer;
import com.Core.model.Product;
import com.Core.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {
	
//	@Autowired
//	private CategoryService categoryService;
//	
	@Autowired 
	private ProductService productService;
	@Autowired
	private CustomerRepository customerRepository;
	

	
	@GetMapping({"/index"})
	public String index(ModelMap modelMap) {
		modelMap.addAttribute("list",productService.findAll());
		
//		for (ProductDto i: productService.findAll()) {
//			System.out.println(i.getImage());
//		}
		modelMap.addAttribute("tiltle","Home Page");
		return "index";
	}
	
	@GetMapping({"/home","/home1"})
	public String homepage(ModelMap modelMap) {
		modelMap.addAttribute("tiltle","Home Page");
		return "home";
	}
	
	@GetMapping("/contact-us")
	public String  contastus(ModelMap modelMap,Principal principal,HttpSession section) {
		modelMap.addAttribute("tiltle","Contact Us");
		if(principal==null) {
			return "redirect:/login";
		}
		String customername=principal.getName();
		Customer customer=customerRepository.findByUsername(customername).get();
		if(customer.getCart()!=null) {
				section.setAttribute("totalItem",customer.getCart().getTotalsItem()+"");
		}else {
			section.setAttribute("totalItem",0+"");		
		}
		return "contact-us";
	}
	
	
	
	
}
