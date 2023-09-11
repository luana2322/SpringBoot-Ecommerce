package com.Core.Service.Impl;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;

import com.Core.Service.ShoppingCartService;
import com.Core.model.CartItem;
import com.Core.model.Customer;
import com.Core.model.Product;
import com.Core.model.ShoppingCart;
import com.Core.repository.CartItemRepository;
import com.Core.repository.CustomerRepository;
import com.Core.repository.ProductRepository;
import com.Core.repository.ShoppingCartRepository;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired 
	private ProductRepository productRepository;

	@Override
	public ShoppingCart addtocart(Product product, int quantity, Customer customer) {

		ShoppingCart shoppingcart = customer.getCart();

		if (shoppingcart == null) {
			shoppingcart = new ShoppingCart();
		}
		shoppingCartRepository.save(shoppingcart);
		Set<CartItem> cartitemList = shoppingcart.getCartItem();

		CartItem cartItem = find(cartitemList, product.getId());
		
		System.out.println("Cart "+ cartItem);

		double unitPrice = product.getCostPrice();

		int itemQuantity = 0;

		if (cartitemList == null) {
			cartitemList = new HashSet<>();
			if (cartItem == null) {
				cartItem = new CartItem();
				cartItem.setProduct(product);
				cartItem.setCart(shoppingcart);
				cartItem.setQuatity(quantity);
				cartItem.setUnitPrice(unitPrice);
				cartItem.setTotalPrice(cartItem.getQuatity()*cartItem.getProduct().getCostPrice());
				cartitemList.add(cartItem);
				cartItemRepository.save(cartItem);
			} else {
				itemQuantity = cartItem.getQuatity() + quantity;
				cartItem.setQuatity(itemQuantity);
				
				cartItem.setTotalPrice(cartItem.getQuatity()*cartItem.getProduct().getCostPrice());
				cartItemRepository.save(cartItem);
			}
		} else {
			if (cartItem == null) {
				cartItem = new CartItem();
				cartItem.setProduct(product);
				cartItem.setCart(shoppingcart);
				cartItem.setQuatity(quantity);
				cartItem.setTotalPrice(cartItem.getQuatity()*cartItem.getProduct().getCostPrice());
				cartItem.setUnitPrice(unitPrice);
				cartitemList.add(cartItem);
				cartItemRepository.save(cartItem);
			} else {
				itemQuantity = cartItem.getQuatity() + quantity;
			cartItem.setQuatity(itemQuantity);
					cartItem.setTotalPrice(cartItem.getQuatity()*cartItem.getProduct().getCostPrice());
				cartItemRepository.save(cartItem);
			}
		}

		System.out.println(cartItem.getTotalPrice()+"tongor tienfe");
		shoppingcart.setCartItem(cartitemList);
		shoppingcart.setCustomer(customer);
		shoppingcart.setTotalsItem(getTotalItem(cartitemList));
		shoppingcart.setTotalPrice(totalPrice(cartitemList));
			 System.out.println(shoppingcart);
		return shoppingCartRepository.save(shoppingcart);
	}

	@Override
	public ShoppingCart updatecart(Product product, int Quantity, Customer customer) {

		return null;
	}
	

	@Override
	public ShoppingCart deleteItemFromcart(Long productid, String cusname) {
			Customer customer=customerRepository.findByUsername(cusname).get();
			ShoppingCart shoppingCart=customer.getCart();
			Set<CartItem> cartItemList=shoppingCart.getCartItem();
			CartItem cartitem=find(cartItemList, productid);
			cartItemList.remove(cartitem);
			cartItemRepository.delete(cartitem);
			
			shoppingCart.setCartItem(cartItemList);
			shoppingCart.setTotalPrice(totalPrice(cartItemList));
			shoppingCart.setTotalsItem(getTotalItem(cartItemList));
		return shoppingCartRepository.save(shoppingCart);
	}

	public int getTotalItem(Set<CartItem> cartitem) {
		return cartitem.size();
	}

	public CartItem find(Set<CartItem> cartitem, Long id) {
		for (CartItem cartItem2 : cartitem) {
			System.out.println(cartItem2.getProduct().getId());
		}
		if (cartitem == null) {
			return null;
		}

		for (CartItem item : cartitem) {
			if (item.getProduct().getId() == id) {
				System.out.println("CÓ SẢn phẩm");
				return item;
			}
		}
		return  null;
	}
	
//	public void setting() {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		sessionFactory.setUnqiueConstraintsEnabled(false);
//
//		Session session = sessionFactory.openSession();
//	}

	public double totalPrice(Set<CartItem> cartitem) {
		double totalPrice = 0;
		for (CartItem item : cartitem) {
			totalPrice += item.getUnitPrice() * item.getQuatity();
		}
		System.out.println(totalPrice);
		return totalPrice;
	}
	
	public ShoppingCart deleteCartById(Long id) {
		ShoppingCart shoppingCart=shoppingCartRepository.getById(id);
		Set<CartItem> cartItems=shoppingCart.getCartItem();
		for (CartItem cartItem : cartItems) {
			cartItemRepository.deleteById(cartItem.getId());
		}
		
		shoppingCart.setCustomer(null);
		shoppingCart.getCartItem().clear();
		shoppingCart.setTotalPrice(0);
		shoppingCart.setTotalsItem(0);
		
		return shoppingCartRepository.save(shoppingCart);
	}

}
