package com.Core.Service;

import com.Core.model.Customer;
import com.Core.model.Product;
import com.Core.model.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart addtocart(Product product,int quantity,Customer customer);
	
	ShoppingCart updatecart(Product product,int Quantity,Customer customer);
	
	ShoppingCart deleteItemFromcart(Long idproduct,String  customername);
	
	ShoppingCart deleteCartById(Long id);
}
