package com.Core.Service.Impl;

import java.util.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Core.Service.UsersService;
import com.Core.dto.UserDto;
import com.Core.model.Role;
import com.Core.model.Users;
import com.Core.repository.RoleRepository;
import com.Core.repository.UsersRepository;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Users save(Users user) {
//		try {
//			if (usersRepository.existsById(Users.getEmail())) {
//				throw new IllegalArgumentException(
//						"User with " + Users.getEmail() + " email already exist!!!");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>());
		return usersRepository.save(user);
	}

	public Users maptouser(UserDto userDto) {
		return new Users(userDto.getUsername(), userDto.getEmail(), userDto.getPassword(), new HashSet<>());
	}

	
	@Override
	public Role saveRole(Role role) {

		return roleRepository.save(role);
	}

	@Override
	public void addtoUser(String user_name, String Rolename) {
		if(!usersRepository.findByEmail(user_name).isPresent()) {
			throw new IllegalArgumentException("User with email "+user_name+" does not exist!!!");
		}
		Role role=roleRepository.findByName(Rolename);
		
		if(role==null) {
			throw new IllegalArgumentException("Role with name "+Rolename+" does not exist!!!");
		}
		Users user=usersRepository.findByEmail(user_name).get();
		
		user.getRoles().add(role);
	}


}
