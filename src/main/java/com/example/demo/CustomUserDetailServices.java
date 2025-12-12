package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailServices implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> op = repo.findByUsername(username);
		if (op.isEmpty()) {
			throw new UsernameNotFoundException("user not found");
		}
		Users u = op.get();
		return User.builder()
				.username(u.getUsername())
				.password(u.getPassword())
				.roles(u.getRole())
				.build();
	}
	

}
