package com.bridgelabz.fundoo.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.bridgelabz.fundoo.notes.service.ILabelService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/user/label")
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
	List<LabelDto> getLabel(@RequestHeader String token){
		List<LabelDto> listLabel = labelService.getAllLabel(token);
		return listLabel;
	}
}