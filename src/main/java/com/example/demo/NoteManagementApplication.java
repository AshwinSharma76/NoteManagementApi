package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteManagementApplication.class, args);
		System.out.println("Welcome to Note Management System");
	}

}
