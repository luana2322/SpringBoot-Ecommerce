package com.Core.model;

import java.util.ArrayList;

import java.util.Collection;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = { "username", "image", "phoneNumber" }))
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	@Column(name = "phoneNumber")
	private String phoneNumber;
	private String address;
	private String password;
	
	@Lob
	@Column(name = "image", columnDefinition = "MEDIUMBLOB")
	private String image;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "city_id")
	private City city;
	
	@OneToOne(mappedBy = "customer",cascade=CascadeType.ALL)
	private ShoppingCart cart;
	
	@OneToMany(mappedBy = "customer")
	private List<Order> orders;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "customer_roles", 
	joinColumns = @JoinColumn(name = "customer_id", 
	referencedColumnName = "customer_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private Collection<Role> roles;

	 public Customer() {
	        this.cart = new ShoppingCart();
	        this.orders = new ArrayList<>();
	    }
}
