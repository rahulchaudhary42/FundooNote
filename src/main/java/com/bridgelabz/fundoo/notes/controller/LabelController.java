package com.bridgelabz.fundoo.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.notes.model.Label;
import com.bridgelabz.fundoo.notes.service.ILabelService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/user/label")
@CrossOrigin(allowedHeaders = "*" ,origins = "*",exposedHeaders= {"jwtToken"} )
public class LabelController {
	
	@Autowired
	private ILabelService labelService;
	
	@PostMapping("/create")
	ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto , @RequestHeader String token) {
		Response statusResponse = labelService.createLabel(labelDto, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	ResponseEntity<Response> deleteLabel(@RequestHeader String token , @RequestParam String labelId) {
		Response statusResponse = labelService.deleteLabel(labelId, token);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	ResponseEntity<Response> updateLabel(@RequestHeader String token , @RequestParam String labelId , @RequestBody LabelDto labelDto){
		Response statusResponse = labelService.updateLabel(labelId, token, labelDto);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}

	@GetMapping("/getlabel")
	List<Label> getLabel(@RequestHeader String token){
		List<Label> listLabel = labelService.getAllLabel(token);
		return listLabel;
	}
	
	@PutMapping("/addlebeltonote")
	ResponseEntity<Response> addNoteToLebel(@RequestParam String labelId , @RequestHeader String token , @RequestParam String noteId){
		Response statusResponse = labelService.addLabelToNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@PutMapping("/removefromnote")
	ResponseEntity<Response> removeFromNote(@RequestHeader String token, @RequestParam String noteId , @RequestParam String labelId){
		Response statusResponse = labelService.removeLabelFromNote(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@PutMapping("/addnotetolabel")
	ResponseEntity<Response> addNoteToLabel(@RequestParam String labelId , @RequestHeader String token , @RequestParam String noteId){
		Response statusResponse = labelService.addNoteToLabel(labelId, token, noteId);
		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getlebelofnote")
	List<Label> getLebelOfNote(@RequestHeader String token, @RequestParam String noteId){
		List<Label> listLabel = labelService.getLebelsOfNote(token, noteId);
		return listLabel;
	}
	
//	@PutMapping("/removefromnote")
//	ResponseEntity<Response> removeFromLabel(@RequestHeader String token, @RequestParam String noteId , @RequestParam String labelId){
//		Response statusResponse = labelService.removeNoteFromLabel(labelId, token, noteId);
//		return new ResponseEntity<Response>(statusResponse,HttpStatus.OK);
//	}
	
}
