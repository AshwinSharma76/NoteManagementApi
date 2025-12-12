package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface NoteRepo extends JpaRepository<Note, Integer> {

	List<Note> findByUserId(int userId);
	
	@Transactional
    @Modifying
    @Query("DELETE FROM Note  WHERE userId = ?1")
    int deleteByUserId(int userId);

}
