package com.bridgelabz.fundoo.user.service;
 

 
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.ResponseToken;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
@Service
public interface IUserServices {
	
	public Response register(UserDTO userDto);
	
	public ResponseToken login(LoginDTO loginDto);
	
	public Response forgotPassword(String email);
	 

}
