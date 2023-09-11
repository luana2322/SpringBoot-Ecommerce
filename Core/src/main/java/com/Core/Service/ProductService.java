package com.Core.Service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Core.dto.ProductDto;
import com.Core.model.Product;


@Service
public interface ProductService {
	List<ProductDto> findAll();
	Product save(MultipartFile multipartFile,ProductDto productDto);
	Product update(ProductDto productDto);
	void deleteById(Long id);
	void enableById(Long id);
	Product saveupdate(MultipartFile multipartFile, ProductDto productDto, Long id);

	Page<Product> searchProducts(int PageNo,String keywords);
	List<ProductDto> getByCategory_Id(Long category_id);
	
	List<ProductDto> sortHighToLow();
	List<ProductDto> sortLowToHigh();
}
