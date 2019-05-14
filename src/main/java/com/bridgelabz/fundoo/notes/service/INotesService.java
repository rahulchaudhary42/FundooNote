package com.bridgelabz.fundoo.notes.service;

import java.util.List;

import org.springframework.stereotype.Service;

 
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.response.Response;
@Service
public interface INotesService {

	public Response createNote(NotesDto notesDto ,String token);

	public Response delete(String token , String userId);

	public Response updateNote(NotesDto notesDto, String token, String userId);
	
	public List<NotesDto>  getAllNotes(String token);
	
	public Response archiveAndUnArchive(String token, String noteId);
	
	
}
