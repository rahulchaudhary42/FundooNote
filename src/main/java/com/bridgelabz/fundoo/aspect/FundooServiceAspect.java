package com.bridgelabz.fundoo.aspect;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;
 
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.notes.dto.NotesDto;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.IUserRepository;
import com.bridgelabz.fundoo.util.UserToken;

@Aspect
@Component
public class FundooServiceAspect {
	
	@Autowired
	private UserToken userToken;
	
	@Autowired
	private IUserRepository userRepository;
	
	@Before(value = "execution(* com.bridgelabz.fundoo.notes.service.NotesServiceImpl.*(..))")
	public void beforeAdvice(JoinPoint joinPoint ) {
//		System.out.println("Before method:" +token+ joinPoint.getSignature());
//		System.out.println("in aop");
//		String userid=userToken.tokenVerify(token);
//		Optional<User> user=userRepository.findById(userid);
//		if(user.isPresent())
//		{
//			System.out.println("user is present");
//		}
//		else
//		{
//			System.out.println("user is not present");
//		}

		//System.out.println("Creating Employee with name - " + name + " and id - " + empId);
	}

	 
}

// @Before(value = "execution(* com.bridgelabz.fundoo.notes.service.NotesServiceImpl.*(..)) and args(notesDto,token)")
