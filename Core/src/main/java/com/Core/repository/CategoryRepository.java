package com.Core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.Core.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
