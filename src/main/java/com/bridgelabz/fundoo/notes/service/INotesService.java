package com.bridgelabz.fundoo.notes.service;

import java.util.List;

import org.springframework.stereotype.Service;

 
import com.bridgelabz.fundoo.notes.dto.NotesDto;
 
import com.bridgelabz.fundoo.response.Response;
@Service
public interface INotesService {

	// Method to create note
	public Response createNote(NotesDto notesDto ,String token);

	// Method to delete note
	public Response delete(String token , String userId);

	// Method to update note
	public Response updateNote(NotesDto notesDto, String token, String userId);
	
	 
	public List<NotesDto>  getAllNotes(String token);
	
	// Method to archive and unarchive
	public Response archiveAndUnArchive(String token, String noteId);
	
	// Method to pin and unpin
	public Response pinAndUnPin(String token, String noteId);
	
	// Method to trash and untrash
	public Response trashAndUnTrash(String token, String noteId);
	
	public List<NotesDto> getArchiveNotes(String token);
	
	public List<NotesDto> getTrashNotes(String token); 
	
	public Response deletePermanently(String token, String noteId);
	
	 
}
