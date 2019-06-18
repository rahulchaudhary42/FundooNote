package com.bridgelabz.fundoo.notes.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.elasticsearch.IElasticsearch;
import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.service.INotesService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/user/note")
@PropertySource("classpath:message.properties")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class NotesController {
	
	Logger logger = LoggerFactory.getLogger(NotesController.class);
	
	@Autowired
	private INotesService noteService;
	
	@Autowired
	IElasticsearch esService;
	
	@PostMapping("/create")
	public ResponseEntity<Response> creatingNote(HttpServletRequest request , @RequestBody NotesDto notesDto , @RequestHeader("token") String token){
		logger.info(notesDto.toString());
		Response responseStatus = noteService.createNote(notesDto, token);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Response> updatingNote(@RequestBody NotesDto notesDto , @RequestHeader String token , @RequestParam String id){
		logger.info(notesDto.toString());
		Response responseStatus = noteService.updateNote(notesDto, token , id);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/delete")
	public ResponseEntity<Response> deletingNote(@RequestHeader String token ,@RequestParam String id){
		Response responseStatus = noteService.delete(token, id);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@GetMapping("/getallnotes")
	public List<Note>  getAllNotes(@RequestHeader String token) {
		 
		return noteService.getAllNotes(token);
	}
	
	@PutMapping("/pin")
	public ResponseEntity<Response> pinNote(@RequestHeader(name = "token") String token , @RequestParam String id){
		Response responseStatus = noteService.pinAndUnPin(token, id);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@PutMapping("/archive")
	public ResponseEntity<Response> archiveNote(@RequestHeader String token , @RequestParam String id){
		Response responseStatus = noteService.archiveAndUnArchive(token, id);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@PutMapping("/trash")
	public ResponseEntity<Response> trashNote(@RequestHeader String token, @RequestParam String id){
		Response responseStatus = noteService.trashAndUnTrash(token, id);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@GetMapping("/getarchivenotes")
	public List<Note>  getArchiveNotes(@RequestHeader String token) {
		List<Note> listnotes = noteService.getArchiveNotes(token);
		return listnotes;
	}
	
	@GetMapping("/gettrashnotes")
	public List<Note>  getTrashNotes(@RequestHeader String token) {
		List<Note> listnotes = noteService.getTrashNotes(token);
		return listnotes;
	}
	
	@DeleteMapping("/deletepermanently")
	public ResponseEntity<Response> deleteNote(@RequestHeader String token, @RequestParam String id){
		Response responseStatus = noteService.deletePermanently(token, id);
		return new ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@PutMapping("/color")
	public ResponseEntity<Response> changeColor(@RequestHeader String token, @RequestParam String noteId,@RequestParam String colorCode) {
		Response responseStatus = noteService.setColor(token, colorCode, noteId);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	
	@GetMapping("/getunpinnednotes")
	public List<Note> getUnPinnedNotes(@RequestHeader String token){
		List<Note> listnotes = noteService.getUnPinnedNotes(token);
		return listnotes;
	}
	
	@GetMapping("/getpinnednotes")
	public List<Note> getPinnedNotes(@RequestHeader String token){
		List<Note> listnotes = noteService.getPinnedNotes(token);
		return listnotes;
	}
	@GetMapping("/searchTitle")
	public List<Note> searchTitle(@RequestParam String title , @RequestParam String token) throws IOException {
	return esService.searchByTitle(title,token);
	}


}
