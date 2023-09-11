package com.Core.Service;

import com.Core.model.Role;
import com.Core.model.Users;

public interface UsersService {

	public Users save(Users user);

	Role saveRole(Role role);

	void addtoUser(String user_name, String name);

	
}
