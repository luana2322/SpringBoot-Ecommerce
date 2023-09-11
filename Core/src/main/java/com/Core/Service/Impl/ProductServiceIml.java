package com.Core.Service.Impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Core.Service.ProductService;
import com.Core.dto.ProductDto;
import com.Core.model.Product;
import com.Core.repository.ProductRepository;

@Service
public class ProductServiceIml implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ImageUploadService imageUploadService;

	@Override
	public List<ProductDto> findAll() {
		List<Product> list = productRepository.findAll();

		List<ProductDto> listdto = new ArrayList<>();

		for (Product product : list) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setActivated(product.is_activated());
			productDto.setCategory(product.getCategory());
			productDto.setCostPrice(product.getCostPrice());
			productDto.setCurrentQuantity(product.getCurrentQuantity());
			productDto.setDeleted(product.is_deleted());
			productDto.setDescription(product.getDescription());
			productDto.setImage(product.getImage());
			productDto.setName(product.getName());
			productDto.setSalePrice(product.getSalePrice());
			listdto.add(productDto);
		}

		return listdto;
	}

	@Override
	public Product save(MultipartFile multipartFile, ProductDto productDto) {
		try {
			Product product = new Product();
			if (multipartFile == null) {
				product.setImage(null);
			} else {
				product.setImage(imageUploadService.storeFile(multipartFile));
			}
			System.out.println(productDto.getCurrentQuantity() + "HHHHHHHH");

			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setCategory(productDto.getCategory());
			product.setCostPrice(productDto.getCostPrice());
			product.setCurrentQuantity(productDto.getCurrentQuantity());

			return productRepository.save(product);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("fail to save rodut");
			return null;
		}

	}

	@Override
	public Product saveupdate(MultipartFile multipartFile, ProductDto productDto, Long id) {
		try {
			Product product = new Product();
			if (multipartFile == null) {
				product.setImage(null);
			} else {
				product.setImage(imageUploadService.storeFile(multipartFile));
			}

			product.setId(id);
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setCategory(productDto.getCategory());
			product.setCostPrice(productDto.getCostPrice());
			product.setCurrentQuantity(productDto.getCurrentQuantity());

			return productRepository.save(product);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Product update(ProductDto productDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
//		Product user = productRepository.findById(id)
		// .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		productRepository.deleteById(id);

	}

	@Override
	public void enableById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<Product> searchProducts(int PageNo, String keywords) {
		Pageable pageable = PageRequest.of(PageNo, 5);
		Page<Product> products = productRepository.searchProducts(keywords, pageable);
		return products;
	}

	@Override
	public List<ProductDto> getByCategory_Id(Long category_id) {
		List<Product> list = productRepository.getByCategory_Id(category_id);

		List<ProductDto> listdto = new ArrayList<>();
		for (Product product : list) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setActivated(product.is_activated());
			productDto.setCategory(product.getCategory());
			productDto.setCostPrice(product.getCostPrice());
			productDto.setCurrentQuantity(product.getCurrentQuantity());
			productDto.setDeleted(product.is_deleted());
			productDto.setDescription(product.getDescription());
			productDto.setImage(product.getImage());
			productDto.setName(product.getName());
			productDto.setSalePrice(product.getSalePrice());
			listdto.add(productDto);
		}
		return listdto;
	}

	@Override
	public List<ProductDto> sortHighToLow() {

		List<ProductDto> sorted = new ArrayList<>();

		sorted = convertmodel(productRepository.sortHighProduct());

		return sorted;
	}

	@Override
	public List<ProductDto> sortLowToHigh() {

		List<ProductDto> sorted = new ArrayList<>();

		sorted = convertmodel(productRepository.sortLowProduct());

		return sorted;
	}

	public List<ProductDto> convertmodel(List<Product> list) {

		List<ProductDto> listdto = new ArrayList<>();

		for (Product product : list) {
			ProductDto productDto = new ProductDto();
			productDto.setId(product.getId());
			productDto.setActivated(product.is_activated());
			productDto.setCategory(product.getCategory());
			productDto.setCostPrice(product.getCostPrice());
			productDto.setCurrentQuantity(product.getCurrentQuantity());
			productDto.setDeleted(product.is_deleted());
			productDto.setDescription(product.getDescription());
			productDto.setImage(product.getImage());
			productDto.setName(product.getName());
			productDto.setSalePrice(product.getSalePrice());
			listdto.add(productDto);
		}

		return listdto;
	}

}
