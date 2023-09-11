package com.Core.dto;



import org.apache.tomcat.util.codec.binary.Base64;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
private Long id;
private String name;
private String description;
private double costPrice;
private double salePrice;
private int currentQuantity;
private com.Core.model.Category category;
private String image;
private boolean activated;
private boolean deleted;

}
