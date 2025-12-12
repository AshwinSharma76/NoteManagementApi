package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminNoteService {

	private final PasswordEncoder passwordEncoder;

	@Autowired
	private NoteRepo repo;
	@Autowired
	private UserServices userServices;

	@Autowired
	private JWTUtil jwtutil;
	
	@Autowired
	private UserRepo userRepo;

	AdminNoteService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public List<Note> getNotes() {
		return repo.findAll();
	}

	public List<Note> getNoteByUid(int userid) {
		return repo.findByUserId(userid);
	}

	public Note getNoteById(int id) {
		Optional<Note> temp = repo.findById(id);
		if (temp.isPresent()) {
			return temp.get();
		}
		return null;
	}

	public boolean deleteNoteById(int id) {
		try {
			repo.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteNotesByUser(int userId) {
		int deletedCount = repo.deleteByUserId(userId);
		return deletedCount > 0;
	}

	public boolean updateCredential(Users user,String token) {
		
		String tok=token.substring(7);
		int id=jwtutil.extractUserId(tok);
		
		Optional<Users> temp = userRepo.findById(id);
		if (temp.isPresent()) {
			user.setId(id);
			user.setRole("ADMIN");
			if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null
					|| user.getPassword().isEmpty()) {
				return false;
			}

			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			Users u = userRepo.save(user);
			if (u != null) {
				return true;
			}

		}
		return false;
	}

}
