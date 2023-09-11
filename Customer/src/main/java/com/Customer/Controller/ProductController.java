package com.Customer.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Core.Service.CategoryService;
import com.Core.Service.ProductService;
import com.Core.Service.Impl.ShoppingCartServiceImpl;
import com.Core.dto.ProductDto;
import com.Core.model.Category;
import com.Core.model.Customer;
import com.Core.model.Product;
import com.Core.repository.CustomerRepository;
import com.Core.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShoppingCartServiceImpl shoppingCartServiceImpl;
	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("/products")
	public String getalltproduct(ModelMap modelMap, Principal principal, HttpSession section) {
		if (principal != null) {
			String customername = principal.getName();
			Customer customer = customerRepository.findByUsername(customername).get();
			if (customer.getCart() != null) {
				section.setAttribute("totalItem", customer.getCart().getTotalsItem() + "");
			} else {
				section.setAttribute("totalItem", 0+"");
			}
		}

		List<ProductDto> listpro = productService.findAll();

		modelMap.addAttribute("tiltle", "Main Shop");
		modelMap.addAttribute("listpro", listpro);
		List<Category> listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
		return "shop";
	}

	@GetMapping("/procate")
	public String getproductfollowcate(@RequestParam Long id, ModelMap modelMap) {
		modelMap.addAttribute("tiltle", categoryService.findById(id).getName());
		List<Category> listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
		modelMap.addAttribute("listpro", productService.getByCategory_Id(id));

		return "shop";
	}

	@GetMapping("/products-detail")
	public String productdetail(@RequestParam Long id, ModelMap modelMap, HttpSession secsion) {

		Product pro = productRepository.findById(id).get();
		modelMap.addAttribute("tiltle", "Product Detail");
		modelMap.addAttribute("pro", pro);
		return "product-detail";
	}

	@GetMapping("/product-high")
	public String highproduct(ModelMap modelMap) {
		modelMap.addAttribute("tiltle", "Product High To Low");
		modelMap.addAttribute("cate", categoryService.findAll());
		modelMap.addAttribute("listpro", productService.sortHighToLow());
		List<Category> listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
		return "shop";
	}

	@GetMapping("/product-low")
	public String lowproduct(ModelMap modelMap) {
		modelMap.addAttribute("tiltle", "Product Low To High");
		modelMap.addAttribute("listpro", productService.sortLowToHigh());
		List<Category> listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
		return "shop";
	}
	
	@GetMapping("/search")
	public String search(ModelMap modelMap
						,Principal principal
						,@RequestParam("keyword") String keyword
						,HttpSession section) {
		
		modelMap.addAttribute("tiltle", "Shop");
		modelMap.addAttribute("listpro", productRepository.searchProducts(keyword));
		List<Category> listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
		if(listcate==null) {
			System.out.println("cate null");
		}else if(productRepository.searchProducts(keyword)==null) {
			System.out.println("product null");
		}
		
		return "shop";
	}
}
