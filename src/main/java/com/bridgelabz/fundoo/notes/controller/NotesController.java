package com.bridgelabz.fundoo.notes.controller;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.notes.service.INotesService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/user/note")
@PropertySource("classpath:message.properties")
public class NotesController {
	
	Logger logger = LoggerFactory.getLogger(NotesController.class);
	
	@Autowired
	private INotesService noteService;
	
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
	
	


}
