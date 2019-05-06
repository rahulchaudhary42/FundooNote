package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.service.IUserServices;

@RestController
@RequestMapping("/user")
public class UserController {
	
	//Creating Logger Object
	static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
 	@Autowired
 	private IUserServices userServices;
	
	// method for registration
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO)
	{
		logger.info("userDTO data"+userDTO.toString());
		logger.trace("User Registration");
		String statusResponse = userServices.register(userDTO);
		return new ResponseEntity<String>(statusResponse, HttpStatus.OK);
	}
	
	// method for login
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDto) throws IllegalArgumentException, UnsupportedEncodingException{
		logger.info("LoginDTO data " + loginDto.toString());
		logger.trace("User Login");
		String statusResponse = userServices.login(loginDto);
		return new ResponseEntity<String>(statusResponse, HttpStatus.OK);
	}
	
 

}
