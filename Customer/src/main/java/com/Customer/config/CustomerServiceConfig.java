package com.Customer.config;

import java.util.stream.Collector;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.Core.model.Customer;
import com.Core.repository.CustomerRepository;

public class CustomerServiceConfig implements UserDetailsService{

	@Autowired
	private CustomerRepository customerRepository;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer=customerRepository.findByUsername(username).get();
		if(customer==null) {
			throw new UsernameNotFoundException("Could not find username");
		}
		System.out.println("CUsotneg"+customer.getPassword());
		return new User(customer.getUsername()
							,customer.getPassword()
							,customer.getRoles()
							.stream()
							.map(role->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
	}

}
