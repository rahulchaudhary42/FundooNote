package com.bridgelabz.fundoo.notes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoo.notes.model.Label;

public interface LabelRepository extends MongoRepository<Label , String>{
	
	 public Label findByLabelIdAndUserId(String labelId , String userId);
	
	public List<Label> findByUserId(String userId);
 
	
	public Optional<Label> findByUserIdAndLabelName(String userId , String labelName);

}
