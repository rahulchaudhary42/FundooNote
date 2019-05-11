package com.bridgelabz.fundoo.notes.service;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.response.Response;
@Service
public interface INotesService {

	public Response createNote(NotesDto notesDto ,String token);
}
