package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServices {

	@Autowired
	private NoteRepo repo;

	public Note createNewNote(Note note) {

		return repo.save(note);
	}
	
	public List<Note>  getNoteById(int id)
	{
		
		return repo.findByUserId(id);
	}
	
	public void  deleteNote(int id)throws Exception
	{

		 repo.deleteById(id);
	}
	
	public Note updateNote(int id,Note note)
	{
		Optional<Note> temp=repo.findById(id);
		if(temp.isPresent())
		{
			if(note.getTitle()==null || note.getTitle().isEmpty())
			{
				note.setTitle(temp.get().getTitle());
			}
			if(note.getContent()==null || note.getContent().isEmpty())
			{
				note.setContent(temp.get().getContent());
			}
			
			return repo.save(note);
		}
		
		return  null;
	}
	
	
}

