package com.Admin.controller;

import java.security.Principal;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Core.Service.CategoryService;
import com.Core.model.Category;
import com.Core.repository.CategoryRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/categories")
	public String categories(ModelMap modelMap, Principal principal, HttpSession section) {
		
		if (principal == null) {
			return "redirect:/login";
		}
		List<Category> list = new ArrayList<>();
		list = categoryService.findAll();
		modelMap.addAttribute("list", list);
		modelMap.addAttribute("size", categoryService.findAll().size());
		modelMap.addAttribute("tiltle", "Category");
		modelMap.addAttribute("newcate", new Category());
		return "categories";
	}

	@PostMapping("/add-category")
	public String add(@ModelAttribute("newcate") Category category
							,ModelMap modelMap
							,RedirectAttributes redirectAttributes
							,HttpSession section) {

		try {
			Category cate1=new Category(category.getName());
			categoryService.save(cate1);
			section.setAttribute("success", "Add Successfully New Category!!!");
		} catch (Exception e) {
			e.printStackTrace();
			section.setAttribute("fail", "Fail to add new Category!!!");
		}
		return "redirect:/categories";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id
							, ModelMap modelMap
							, RedirectAttributes redirectAttributes
							,HttpSession section) {
		
		section.removeAttribute("success");
		section.removeAttribute("fail");
		try {
			Category user = categoryRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
			categoryRepository.delete(user);
			section.setAttribute("success", "Delete Successfully New Category!!!");
		} catch (Exception e) {
			e.printStackTrace();
			section.setAttribute("fail", "Fail to delete new Category!!!");
		}
		return "redirect:/categories";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id
			, ModelMap modelMap
			, HttpSession section) {
		section.removeAttribute("success");
		section.removeAttribute("fail");
		
		Category cate = categoryService.findById(id);
		// categoryRepository.findById(id)
		// .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		section.removeAttribute("cate");
		section.removeAttribute("cate1");

		System.out.println("khfbibafbalbfalbflhbalfbba");
		System.out.println(cate.getName());
		section.setAttribute("idupdate", id);

		section.setAttribute("cate", new Category());
		section.setAttribute("cate1", cate.getName());
		return "redirect:/categories";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute("cate") Category category, ModelMap modelMap, BindingResult result,
			HttpSession section) {
		section.removeAttribute("cate");
		section.removeAttribute("cate1");

		Long id = (Long) section.getAttribute("idupdate");

		Category cate1 = categoryService.findById(id);
		cate1.setId(id);
		cate1.setName(category.getName());

		try {
			categoryService.update(cate1);
			section.setAttribute("success", "Update Successfully Category!!!");

		} catch (Exception e) {
			section.setAttribute("fail", "Fail to Update Category!!!");
		}

		return "redirect:/categories";
	}

}
