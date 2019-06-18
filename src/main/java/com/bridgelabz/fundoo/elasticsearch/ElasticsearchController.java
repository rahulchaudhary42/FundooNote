package com.bridgelabz.fundoo.elasticsearch;

import java.io.IOException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bridgelabz.fundoo.notes.model.Note;

@RestController
@RequestMapping("/EsNote")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ElasticsearchController {

	@Autowired(required = true)
	IElasticsearch esService;

	@PostMapping(value = "/createnote")
	public String createNote(@RequestBody Note note) throws IOException {
		return esService.createNote(note);
	}
	
	@DeleteMapping(value = "/delete")
	public String deleteNote(@PathParam(value = "id") String id) throws Exception {
	System.out.println("!!!!!!!");
	return esService.deleteNote(id);
	}
	
	@PostMapping(value = "/find")
	public Note findById(@PathParam(value="id") String id) throws Exception {
	System.out.println("!!!!!!!");
	return esService.findById(id);
	}
	
	@PutMapping("/update")
	public String updateNote(@RequestBody Note note) throws Exception
	{
	return esService.updateNote(note);
	}
	
 	@GetMapping("/searchTitle")
	public List<Note> searchTitle(@RequestParam String title , @RequestParam String token) throws IOException {
	return esService.searchByTitle(title,token);
	}

}
