package com.Core.Service.Impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Core.Service.CategoryService;
import com.Core.model.Category;
import com.Core.repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}

	@Override
	public Category findById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id).get();
				//categoryRepository.findById(id).isPresent() ? categoryRepository.findById(id).get() : null;
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		Category categoryupdate=new Category();
		categoryupdate.setId(category.getId());
		categoryupdate.setName(category.getName());
		categoryupdate.set_activated(category.is_activated());
		categoryupdate.set_deleted(category.is_deleted());
		System.out.println("entity"+categoryupdate);
		return categoryRepository.save(categoryupdate);
	}

	@Override
	public void deleteById(Long id) {
		Category category=categoryRepository.getById(id);
		category.set_activated(false);
		category.set_deleted(true);
		categoryRepository.save(category);
	}

	@Override
	public void enableById(Long id) {
		Category category=categoryRepository.getById(id);
		category.set_activated(true);
		category.set_deleted(false);
		categoryRepository.save(category);
	}

}
