package com.bridgelabz.fundoo.user.service;
 

 
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.PasswordDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
@Service
public interface IUserServices {
	
	// Method to register
	public Response register(UserDTO userDto);
	
	// Method to login
	public ResponseToken login(LoginDTO loginDto);
	
	// Method for forgot password
	public Response forgotPassword(String email);
	
	// Method to validate email
	public Response validateEmail(String token);
	
	// Resetting Password
	public Response resetPassword(PasswordDTO passwordDto , String token);
	 public Response reset(String password, String token);
	
	 

}
