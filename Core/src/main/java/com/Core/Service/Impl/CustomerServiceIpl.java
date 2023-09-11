package com.Core.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Core.Service.CustomerService;
import com.Core.dto.CustomerDto;
import com.Core.model.Customer;
import com.Core.repository.CustomerRepository;
@Service
public class CustomerServiceIpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Customer save(CustomerDto customerDto) {
		Customer customer=new Customer();
		customer.setUsername(customerDto.getUsername());
		customer.setPhoneNumber(customerDto.getPhoneNumber());
		customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
		return customerRepository.save(customer);
	}

}
