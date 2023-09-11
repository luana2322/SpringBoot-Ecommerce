package com.Customer.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.Core.Service.CategoryService;
import com.Core.Service.OrderService;
import com.Core.Service.ProductService;
import com.Core.dto.ProductDto;
import com.Core.model.Category;
import com.Core.model.Customer;
import com.Core.model.ShoppingCart;
import com.Core.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CheckoutController {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/checkout")
	public String checkout(ModelMap modelMap
							,HttpSession section
							,Principal principal) {
		if(principal==null) {
			return "redirect:/login";
		}
		
			
		Customer customer=customerRepository.findByUsername(principal.getName()).get();
		ShoppingCart shoppingCart=customer.getCart();
		
		modelMap.addAttribute("shoppingcart",shoppingCart);
		
		if(shoppingCart.getTotalsItem()==0) {
			modelMap.addAttribute("notification","No product");
			return "card";
		}
		modelMap.addAttribute("totalprice",shoppingCart.getTotalPrice()+"");
		if (customer.getCart() != null) {
			modelMap.addAttribute("totalprice",shoppingCart.getTotalPrice()+"");	
			section.setAttribute("totalItem", customer.getCart().getTotalsItem() + "");
		} else {
			modelMap.addAttribute("totalprice",0+"");
			section.setAttribute("totalItem", 0+"");
		}
		return "checkout";   
	}
	
	@GetMapping("/order")
	public String order(Principal principal
						,ModelMap modelMap
						,HttpSession section) {
		if(principal==null) {
			return "redirect:/login";
		}
		orderService.order(principal.getName());
		String customername = principal.getName();
		Customer customer = customerRepository.findByUsername(customername).get();
		if (customer.getCart() != null) {
			section.setAttribute("totalItem", customer.getCart().getTotalsItem() + "");
		} else {
			section.setAttribute("totalItem", 0+"");
		}
	

	List<ProductDto> listpro = productService.findAll();

	modelMap.addAttribute("tiltle", "Main Shop");
	modelMap.addAttribute("listpro", listpro);
	List<Category> listcate = categoryService.findAll();
	modelMap.addAttribute("listcate", listcate);
		return "shop";
		
		
	}
	
}
