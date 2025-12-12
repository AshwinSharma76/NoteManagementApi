package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>{

	@Query("from Users where username=  ?1")
	Optional<Users> findByUsername(String username);
	
	@Query("from Users where role='ADMIN'")
	Optional<Users> findAdminRole();
}
