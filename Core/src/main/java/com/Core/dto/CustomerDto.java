package com.Core.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
	
	private String firstName;
	private String lastName;
	@Size(min = 3,max=20,message = "Invalid Username")
	private String username;
	@Size(min = 5,max=12,message = "Invalid phoneNumber")
	private String phoneNumber;
	@Size(min = 3,max=20,message = "Invalid Password")
	private String password;
	@Size(min = 3,max=20,message = "Invalid Password")
	private String confirmpassword;
}
