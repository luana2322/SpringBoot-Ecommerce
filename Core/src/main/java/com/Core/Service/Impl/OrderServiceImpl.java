package com.Core.Service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Core.Service.OrderService;
import com.Core.Service.ShoppingCartService;
import com.Core.model.CartItem;
import com.Core.model.Customer;
import com.Core.model.Order;
import com.Core.model.OrderDetail;
import com.Core.model.ShoppingCart;
import com.Core.repository.CustomerRepository;
import com.Core.repository.OderRepository;
import com.Core.repository.OrderDetailRepository;

@Service
public class OrderServiceImpl implements OrderService {
@Autowired
private CustomerRepository customerRepository;
@Autowired
private OderRepository oderRepository;
@Autowired
private OrderDetailRepository orderDetailRepository;
@Autowired
private ShoppingCartService shoppingCartService;
	
	@Override
	public Order order(String customername) {
		Customer customer=customerRepository.findByUsername(customername).get();
		ShoppingCart shoppingCart=customer.getCart();
		Order order=new Order();
		order.setCustomer(customer);
		order.setDeliveryDate(new Date());
		order.setNotes("Hàng dễ vỡ!!!");
		order.setOderDate(new Date());
		order.setShippingFee(0);
		order.setOderStatus("Pending");
		order.setTotalPrice(shoppingCart.getTotalPrice());
		List<OrderDetail> list=new ArrayList<>();
		
		for (CartItem item: shoppingCart.getCartItem()) {
			OrderDetail orderDetail=new OrderDetail();
			orderDetail.setProduct(item.getProduct());
			orderDetail.setOrder(order);
			orderDetail.setQuatity(item.getQuatity());
			orderDetail.setTotalPrice(item.getTotalPrice());
			orderDetail.setUnitPrice(item.getUnitPrice());
			list.add(orderDetail);
			orderDetailRepository.save(orderDetail);
		}
		order.setOdeDetails(list);
		shoppingCartService.deleteCartById(shoppingCart.getId());
		return oderRepository.save(order);
	}

}
