package com.bridgelabz.fundoo.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
