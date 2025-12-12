package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController

public class AuthController {

	@Autowired
	private UserServices userServices;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailServices customUserDetailServices;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody Users user) {
		Users u=userServices.createUser(user);
		System.out.println("\nCreate User:- "+ u);
		if(u==null)
		{
			return ResponseEntity.status(HttpStatus.FOUND).body("Admin Already Exists");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(u);
	}
	
	@PostMapping("/login")
	public String authenticate(@RequestBody AuthRequest auth)
	{
		try
		{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							auth.getUsername(), auth.getPassword())
					);
			
			int uid=userServices.getIdUser(auth.getUsername());
			return jwtUtil.genrateToken(auth.getUsername(),uid);
			
		}catch(Exception e)
		{
			throw e;
		}
	}
	
	@GetMapping("/")
	public String getHome()
	{
		return "Welcome to the NoteManagement";
	}
	
}
