package com.Core.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int quatity;
	private double totalPrice;
	private double unitPrice;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="shopping_cart_id",referencedColumnName = "shopping_cart_id")
	private ShoppingCart cart;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="product_id",referencedColumnName = "product_id")
	private Product product;
}
