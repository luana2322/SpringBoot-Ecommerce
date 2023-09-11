package com.Core.Service;

import java.util.List;

import com.Core.model.Category;



public interface CategoryService {
	List<Category> findAll();

	Category save(Category category);

	Category findById(Long id);

	Category update(Category category);

	void deleteById(Long id);

	void enableById(Long id);
}
