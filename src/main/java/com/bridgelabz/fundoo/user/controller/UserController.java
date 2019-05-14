package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.PasswordDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.service.IUserServices;

@RestController
@RequestMapping("/user")
@PropertySource("classpath:message.properties")
public class UserController {
	
	//Creating Logger Object
	static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
 	@Autowired
 	private IUserServices userServices;
	
	// method for registration
	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO)
	{
		logger.info("userDTO data"+userDTO.toString());
		logger.trace("User Registration");
		Response statusResponse = userServices.register(userDTO);
		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);
	}
	
	// method for login
	@PostMapping("/login")
	public ResponseEntity<ResponseToken> login(@Valid @RequestBody LoginDTO loginDto) throws IllegalArgumentException, UnsupportedEncodingException{
		logger.info("LoginDTO data " + loginDto.toString());
		logger.trace("User Login");
		ResponseToken statusResponse = userServices.login(loginDto);
		return new ResponseEntity<ResponseToken>(statusResponse, HttpStatus.OK);
	}
	
	@GetMapping("/emailvalidation/{token}")
	public ResponseEntity<Response> validateEmail(@PathVariable String token){
		Response statusResponse = userServices.validateEmail(token);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.ACCEPTED);
		
	}
	
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email){
		logger.info("User email : " + email);
		Response statusResponse = userServices.forgotPassword(email);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.OK);
	}
	
	@PutMapping("/resetpassword/{token}")
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody PasswordDTO passwordDto,@RequestParam String token){
		Response statusResponse = userServices.resetPassword(passwordDto, token);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.OK);
		
	}
 

}
