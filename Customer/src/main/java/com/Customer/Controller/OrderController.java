package com.Customer.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.Core.Service.Impl.ShoppingCartServiceImpl;
import com.Core.model.CartItem;
import com.Core.model.Customer;
import com.Core.model.ShoppingCart;
import com.Core.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ShoppingCartServiceImpl shoppingCartServiceImpl;

	@GetMapping("/cart")
	public String cart(ModelMap modelMap
						, HttpSession section
						, Principal principal) {
		modelMap.addAttribute("tiltle", "Cart");
		if (principal == null) {
			return "redirect:/login";
		}
		Customer customer = customerRepository.findByUsername(principal.getName()).get();
		ShoppingCart cart = customer.getCart();
		

		modelMap.addAttribute("shoppingcart", cart);
		
		if(cart.getTotalsItem()!=0) {
			modelMap.addAttribute("totalprice", cart.getTotalPrice());
			section.setAttribute("totalItem", cart.getTotalsItem());
		}else {
			modelMap.addAttribute("totalprice","$"+0);
			section.setAttribute("totalItem", 0+"");
		}
		
		return "cart";
	}

	@GetMapping("/delete-cartitem")
	public String delete(@RequestParam Long id
							, ModelMap modelMap
							, Principal principal
							,HttpSession section) {
		modelMap.addAttribute("tiltle", "Cart");
		if (principal == null) {
			return "redirect:/login";
		}
		shoppingCartServiceImpl.deleteItemFromcart(id, principal.getName());
		Customer customer = customerRepository.findByUsername(principal.getName()).get();
		ShoppingCart cart = customer.getCart();

		modelMap.addAttribute("shoppingcart", cart);
		modelMap.addAttribute("totalprice", cart.getTotalPrice());
		if(cart!=null) {
			section.setAttribute("totalItem", cart.getTotalsItem());
		}else {
			section.setAttribute("totalItem", 0+"");

			modelMap.addAttribute("notification","Noproduct");
		}

		return "cart";
	}

}
