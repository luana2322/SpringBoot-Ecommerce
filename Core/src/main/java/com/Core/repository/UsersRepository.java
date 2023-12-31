package com.Core.repository;

import java.util.Optional;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Core.model.Users;



@Repository
public interface UsersRepository extends JpaRepository<Users, String>{

	Optional<Users> findByEmail(String email);
	Optional<Users> findByUsername(String username);
	Optional<Users> findByPassword(String password);

}
