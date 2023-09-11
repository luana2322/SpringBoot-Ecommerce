package com.Core.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	@Size(min=5,max=20,message="Invalid username!!(5-20 character)")
	private String username;
	
	private String email;
	
	@Size(min=5,max=20,message="Invalid username!!(5-20 character)")
	private String password;
	
	private String repeatpassword;

}
