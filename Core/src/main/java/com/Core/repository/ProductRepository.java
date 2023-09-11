package com.Core.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Core.model.Product;
import com.Core.model.Category;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "select p from Product p where p.description like %?1% or p.name like %?1%")
	Page<Product> searchProducts(String keywords,Pageable pageable);
	
	 List<Product> getByCategory_Id(Long category_id);
	 
	 @Query(value = "select * from product order by product.costPrice desc",nativeQuery = true)
		List<Product> sortHighProduct();
	 
	 @Query(value = "select * from product order by product.costPrice",nativeQuery = true)
		List<Product> sortLowProduct();
		
	 @Query("SELECT p FROM Product p WHERE " +
	            "p.name LIKE CONCAT('%',:query, '%')" +
	            "Or p.description LIKE CONCAT('%', :query, '%')")
	    List<Product> searchProducts(String query);
}
