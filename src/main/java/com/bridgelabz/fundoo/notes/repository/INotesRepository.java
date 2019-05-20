package com.bridgelabz.fundoo.notes.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.notes.model.Label;
import com.bridgelabz.fundoo.notes.model.Note;
@Repository
public interface INotesRepository extends MongoRepository<Note, String>{
	
	public Note findByIdAndUserId(String id , String userId);
	
//	@Query("{'usertab.userId':?0}")
  //  List<Note> findByUserId(String userId);
	 List<Note> findByUserId(String id);
	 
	 public List<Label> findAllListLabelById(String id);
}
