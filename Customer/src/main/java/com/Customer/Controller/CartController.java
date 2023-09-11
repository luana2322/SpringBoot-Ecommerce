package com.Customer.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.Core.Service.CategoryService;
import com.Core.Service.ProductService;
import com.Core.Service.ShoppingCartService;
import com.Core.dto.ProductDto;
import com.Core.model.Category;
import com.Core.model.Customer;
import com.Core.repository.CustomerRepository;
import com.Core.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/addtocart")
	public String addtocard(@RequestParam Long id, ModelMap modelMap, HttpSession section, Principal principal) {
		int quantity = 1;
		if (principal == null) {
			return "redirect:/login";
		}
		String customername = principal.getName();
		Customer customer = customerRepository.findByUsername(customername).get();

		List<ProductDto> listpro = productService.findAll();

		modelMap.addAttribute("tiltle", "Main Shop");
		modelMap.addAttribute("listpro", listpro);
		List<Category> listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
		shoppingCartService.addtocart(productRepository.findById(id).get(), quantity, customer);
		if (customer.getCart() != null) {
			section.setAttribute("totalItem", customer.getCart().getTotalsItem() + "");
		} else {
			section.setAttribute("totalItem", 0+"");
		}
		return "shop";
	}

}
