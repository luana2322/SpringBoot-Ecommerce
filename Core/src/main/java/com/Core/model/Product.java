package com.Core.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product",uniqueConstraints = @UniqueConstraint(columnNames = {"name","image"}))
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private Long id;
	private String name;
	private String description;
	private double costPrice;
	private double salePrice;
	private int currentQuantity;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String image;

//	cascade = CascadeType.ALL
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id",referencedColumnName = "category_id")
	private Category category;
	private boolean is_deleted;
	private boolean is_activated;
	
	

}
