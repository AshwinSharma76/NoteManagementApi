package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminNoteService adminNoteService;
	

	
	@GetMapping("/note")
	public ResponseEntity<List<Note>> getAllNote()
	{
		return ResponseEntity.ok(adminNoteService.getNotes());
	}
	
	@GetMapping("/note/user/{id}")
	public ResponseEntity<List<Note>> getNoteByUserId(@PathVariable("id") int userid)
	{
		List<Note> list=adminNoteService.getNoteByUid(userid);
		return   ResponseEntity.ok(list);
	}
	
	
	@GetMapping("/note/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable("id") int id)
	{
		Note note=adminNoteService.getNoteById(id);
		return   ResponseEntity.ok(note);
	}
	
	@DeleteMapping("/note/{id}")
	public ResponseEntity<?> deleteNoteByID(@PathVariable("id") int id)
	{
		boolean t=adminNoteService.deleteNoteById(id);
		if(t)
		{
			return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Note Not Found");
		}
	}
	
	@DeleteMapping("/note/user/{id}")
	public ResponseEntity<?> deleteNoteByUserID(@PathVariable("id") int id)
	{
		boolean t=adminNoteService.deleteNotesByUser(id);
		if(t)
		{
			return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Note Not Found on: "+id);
		}
	}
	
	@PostMapping("/updateCredential")
	public ResponseEntity<?> updateCredential(@RequestBody Users u,@RequestHeader("Authorization") String token)
	{
		
		boolean t=adminNoteService.updateCredential(u,token);
		if(t)
		{
			return ResponseEntity.status(HttpStatus.OK).body("Credential updated Successfully");
		}
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not data found or invaliad Credential");

		
	}
	
}
