package com.bridgelabz.fundoo.elasticsearch;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.model.Note;

@Service
public interface IElasticsearch {
	
	String createNote(Note note) throws IOException;
	public String deleteNote(String id) throws IOException;
	public Note findById(String id) throws Exception;
	public String updateNote(Note note) throws Exception;
	public List<Note> searchByTitle(String title,String userId) throws IOException;

}
 