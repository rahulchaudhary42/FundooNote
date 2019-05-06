package com.bridgelabz.fundoo.user.service;

 
import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
 
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
 
@Service("userService")
public class UserServicesImplementation implements IUserServices{
	   
	private static final Logger log = LoggerFactory.getLogger(UserServicesImplementation.class);
	

	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public String register(UserDTO userDTO) {
		 
		log.info(userDTO.toString());

		//getting user record by email
		Optional<User> avaiability = userRepository.findByEmail(userDTO.getEmail());

		//Checking whether the user is existing or not  
		if(avaiability.isPresent()){
			throw new RegistrationException("User exist", -2);
		}
		User user = modelMapper.map(userDTO, User.class);//storing value of one model into another

		user.setRegisteredDate(LocalDate.now());

		User saveResponse = userRepository.save(user);

		//Checking whether data is stored successfully in database
		if(saveResponse==null)
		{
			throw new RegistrationException("Data is not saved in database", -2);
		}
		log.info(saveResponse.toString());
		return null;
	}

	@Override
	public String login(LoginDTO loginDto) {
		Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
		log.info("User Password : " + user.get().getPassword());
		return null;
	}
	
	public User verify(User user) {
		log.info("User : " + user);
		user.setVarified(true);
		user.setUpdatedDate(LocalDate.now());
		log.info("User : "+user);
		return userRepository.save(user);
	}
	

 

}
