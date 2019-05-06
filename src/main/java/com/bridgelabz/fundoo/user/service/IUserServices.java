package com.bridgelabz.fundoo.user.service;
 

 
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
 
import com.bridgelabz.fundoo.user.dto.UserDTO;
@Service
public interface IUserServices {
	
	public String register(UserDTO userDto);
	
	public String login(LoginDTO loginDto);
	
	 

}
