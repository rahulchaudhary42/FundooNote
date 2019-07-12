package com.bridgelabz.fundoo.notes.service;

import java.util.List;

import org.springframework.stereotype.Service;

 
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.response.Response;
@Service
public interface INotesService {

	// Method to create note
	Response createNote(String token, NotesDto notesDto);

	// Method to delete note
	public Response delete(String token , String userId);

	// Method to update note
	public Response updateNote(NotesDto notesDto, String token, String userId);
	
	 
	public List<Note>  getAllNotes(String token);
	
	// Method to archive and unarchive
	public Response archiveAndUnArchive(String token, String noteId);
	
	// Method to pin and unpin
	public Response pinAndUnPin(String token, String noteId);
	
	// Method to trash and untrash
	public Response trashAndUnTrash(String token, String noteId);
	
	public List<Note> getArchiveNotes(String token);
	
	public List<Note> getTrashNotes(String token); 
	
	public Response deletePermanently(String token, String noteId);
	
	//public List<NotesDto> getPinnedNotes(String token);
	public List<Note> getUnPinnedNotes(String token);
	
	public List<Note> getPinnedNotes(String token);
	 
	public Response setColor(String token , String colorCode , String noteId);
	
	public Response addReminder(String token, String noteId, String time); 
	
	public String getRemainders(String token, String noteId);

	
}
