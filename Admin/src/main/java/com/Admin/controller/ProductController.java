package com.Admin.controller;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Core.Service.CategoryService;
import com.Core.Service.ProductService;
import com.Core.Service.Impl.ProductServiceIml;
import com.Core.dto.ProductDto;
import com.Core.model.Category;
import com.Core.model.Product;
import com.Core.repository.ProductRepository;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProductController {
	private final int itemperpage = 5;
	@Autowired
	private ProductServiceIml productServiceIml;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductRepository productRepository;

	public int getpage(int itemperpage, int allitem) {
		int value = allitem % itemperpage;
		int page = allitem / itemperpage;
		if ((value) == 0) {
			return page;
		} else {
			return page++;
		}
	}

	@GetMapping("/products")
	public String products(ModelMap modelMap, Principal principal, HttpSession section) {
		section.removeAttribute("success");
		section.removeAttribute("fail");

		if (principal == null) {
			return "redirect:/login";
		}
		List<ProductDto> list = productService.findAll();

//		number page
		int allitem = list.size();
		int page1 = getpage(itemperpage, allitem);
		List<Integer> listpage = IntStream.rangeClosed(1, page1).boxed().collect(Collectors.toList());
		modelMap.addAttribute("page", listpage);
//		number page
//		cate
		List<Category> listcate = new ArrayList<>();
		listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
//		cate
		// form add

		modelMap.addAttribute("newproduct", new ProductDto());
		// form add

		modelMap.addAttribute("products", list);
		modelMap.addAttribute("size", list.size());
		return "products";
	}

	@PostMapping("/add-product")
	public String saveproduct(@ModelAttribute("product") ProductDto productDto,
			@RequestParam("imageproduct") MultipartFile multipartFile, HttpSession section) {
		section.removeAttribute("success");
		section.removeAttribute("fail");
		productService.save(multipartFile, productDto);
		return "redirect:/products";
	}

	@GetMapping("/delete-product/{id}")
	public String deleteproduct(@PathVariable("id") Long id, ModelMap modelMap, HttpSession section) {
		section.removeAttribute("success");
		section.removeAttribute("fail");
		try {

		} catch (Exception e) {
			e.printStackTrace();
			section.setAttribute("fail", "Delete Failed!!!");
		}
		productService.deleteById(id);
		section.setAttribute("success", "Delete Success!!!");
		return "redirect:/products";
	}

	@GetMapping("/edit-product/{id}")
	public String getupdatepage(@PathVariable("id") Long id, ModelMap modelMap) {

		List<Category> listcate = new ArrayList<>();
		listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);

		Product product = productRepository.findById(id).get();
		modelMap.addAttribute("productupdate", product);
		modelMap.addAttribute("product", new ProductDto());
		return "update-product";
	}

	@PostMapping("/update-product/{id}")
	public String updateproduct(@PathVariable("id") Long id, @ModelAttribute("product") ProductDto productDto,
			@RequestParam("imageproduct") MultipartFile multipartFile, ModelMap modelMap) {

		productService.saveupdate(multipartFile, productDto, id);

		List<ProductDto> list = productService.findAll();

		List<Category> listcate = new ArrayList<>();
		listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);

		modelMap.addAttribute("products", list);
		modelMap.addAttribute("size", list.size());
		modelMap.addAttribute("newproduct", new ProductDto());

		return "products";
	}

	@GetMapping("/products/{page1}")
	public String getitemperpage(@PathVariable("page1") int page, ModelMap modelMap, HttpSession section,
			Principal principal) {

		if (principal == null) {
			return "redirect:/login";
		}
		List<ProductDto> list = productService.findAll();

//		pagination
		List<ProductDto> listreal = list.subList(((page - 1) * itemperpage), (page * itemperpage));
		modelMap.addAttribute("products", listreal);
		modelMap.addAttribute("size", list.size());
//		end pagination
//		number page
		int allitem = list.size();
		int page1 = getpage(itemperpage, allitem);
		List<Integer> listpage = IntStream.rangeClosed(1, page1).boxed().collect(Collectors.toList());
		modelMap.addAttribute("page", listpage);
//		number page
//		cate
		List<Category> listcate = new ArrayList<>();
		listcate = categoryService.findAll();
		modelMap.addAttribute("listcate", listcate);
//		cate
		// form add

		modelMap.addAttribute("newproduct", new ProductDto());
		// form add

		return "products";
	}
	
	@GetMapping("/search/{pageNo}")
	public String searchProducts(@PathVariable("pageNo")int pageNo
									,@RequestParam("keyword") String keyword
									,ModelMap modelMap
									,Principal principal) {
		if(principal==null) {
			return "redirect:/login";
		}
		
		Page<Product> products=productService.searchProducts(pageNo, keyword);
		modelMap.addAttribute("tiltle","Search Result");
		modelMap.addAttribute("products",products);
		modelMap.addAttribute("size",products.getSize());
		modelMap.addAttribute("currentPage",pageNo);
		modelMap.addAttribute("totalPage",products.getTotalPages());
		
	
		return "products";
	}
}
