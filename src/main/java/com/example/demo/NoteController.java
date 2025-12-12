package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteServices noteServices;
	@Autowired
	private JWTUtil jwtutil;

	@PostMapping
	public ResponseEntity<?> addNote(@RequestBody Note note, @RequestHeader("Authorization") String token) {
		String btoken = null;
		Integer uid = null;
		System.out.println("Token:-  " + token);
		if (token != null && token.startsWith("Bearer ")) {
			btoken = token.substring(7);
			uid = jwtutil.extractUserId(btoken);
			note.setUserId(uid);
			Note n = noteServices.createNewNote(note);
			if (n != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(n);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create a note");

			}
		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create a note");

	}

	@GetMapping
	public ResponseEntity<?> getAllNote(@RequestHeader("Authorization") String token) {
		String btoken = null;
		Integer uid = null;

		if (token != null && token.startsWith("Bearer ")) {
			btoken = token.substring(7);
			uid = jwtutil.extractUserId(btoken);

			List<Note> notes = noteServices.getNoteById(uid);

			return ResponseEntity.ok(notes);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
	}

	@DeleteMapping("/{id}")
	public String deleteNote(@PathVariable("id") int id) throws Exception
	{
		try {
			noteServices.deleteNote(id);
			return "Deleted Successfully";
		} catch (Exception e) {
			
			throw e;
		}
		
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateNote(@PathVariable("id") int id,@RequestBody Note note)
	{
		Note getNote=noteServices.updateNote(id, note);
		if(note==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object Note found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(getNote);
	}
	
	
}
