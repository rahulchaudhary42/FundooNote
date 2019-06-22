package com.bridgelabz.fundoo.user.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.PasswordDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.service.AmazonService;
import com.bridgelabz.fundoo.user.service.IUserServices;

@RestController
@RequestMapping("/user")
@PropertySource("classpath:message.properties")
@CrossOrigin(origins = "*",allowedHeaders = "*",exposedHeaders= {"jwtToken"})
public class UserController {
	
	//Creating Logger Object
	static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
 	@Autowired
 	private IUserServices userServices;
 	
 	@Autowired
 	private AmazonService amazonClient;
	
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
	
	// // method for email validation
	@GetMapping("/emailvalidation/{token}")
	public ResponseEntity<Response> validateEmail(@PathVariable String token){
		Response statusResponse = userServices.validateEmail(token);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.ACCEPTED);
		
	}
	
	// method for forgot password
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email){
		logger.info("User email : " + email);
		Response statusResponse = userServices.forgotPassword(email);
		return new ResponseEntity<Response> (statusResponse, HttpStatus.OK);
	}
	 
	
    // method for reset password
	@PutMapping("/resetpassword")
	public ResponseEntity<Response> resetPassword(@RequestParam String password , @RequestHeader (value="jwtToken") String token){
		Response statusResponse = userServices.reset(password, token);
		return new ResponseEntity<Response> (statusResponse,HttpStatus.OK);
		
	}
	
	@GetMapping("/getProfile")
	public URL getProfilePic(@RequestHeader String token)
	{
		URL profile=amazonClient.getProfile(token);
		return profile;
	}
	
	
	
 

}
