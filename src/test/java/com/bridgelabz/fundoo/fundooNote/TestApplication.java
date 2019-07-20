package com.bridgelabz.fundoo.fundooNote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.user.service.MailService;
import com.bridgelabz.fundoo.user.service.UserServicesImplementation;
import com.bridgelabz.fundoo.util.UserToken;



public class TestApplication {
	
	@Mock
	private Environment environment;

	@Mock
	private IUserRepository userRepository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private MailService mailServise;

	@Mock
	private UserToken userToken;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private JavaMailSender javaMailSender;
	
	@InjectMocks
	UserServicesImplementation userimpl;
	
//	@Test
//	public void Test()
//	{
//		User user=new User();
//		user.setEmail("shrs601@gmail.com");
//		LoginDTO loginDto=new LoginDTO("shrs601@gmail.com","1234");
//		when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
//		assertEquals(Mockito.any(), userimpl.login(loginDto));
//	}
	

}
