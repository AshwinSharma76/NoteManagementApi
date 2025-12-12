package com.example.demo;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

	@Autowired
	private UserRepo repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Users createUser(Users user) {
		boolean t=isAdminPresent();
		//System.out.println("\n admin Value:-"+ t);
		if(t && user.getRole().toUpperCase().equals("ADMIN"))
		{
			return null;
		}
		else
		{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return repo.save(user);
		}
		
	}

	public int getIdUser(String username)
	{
		Optional<Users> user=repo.findByUsername(username);
		
		if(user.isPresent())
		{
			return user.get().getId();
		}
		else
		{
			throw new RuntimeException("No User Found");
		}
		
	}
	
	public boolean isAdminPresent()
	{
		
		Optional<Users> temp=repo.findAdminRole();
		//System.out.print("Admin: -"+ temp.get().getUsername() + "  "+temp.get().getRole());
		if(temp.isPresent() )
		{
			return true;
		}
		return false;
	}
}
