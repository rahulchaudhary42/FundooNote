package com.bridgelabz.fundoo.aspect;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

	Logger logger = LoggerFactory.getLogger(FundooServiceAspect.class);
	/*
	 * 
	 * @Before(value =
	 * "execution(* com.bridgelabz.fundoo.*.*.*.*(..)) and args(token,*)") public
	 * void beforeAdvice(JoinPoint joinPoint, String token) {
	 * System.out.println("Before method:" + joinPoint.getSignature());
	 * System.out.println("In aop"); logger.info("I am in AOP..."); String userid =
	 * userToken.tokenVerify(token); Optional<User> user =
	 * userRepository.findById(userid); if (user.isPresent()) {
	 * System.out.println("user is present"); } else {
	 * System.out.println("user is not present"); } }
	 */
}

// @Before(value = "execution(* com.bridgelabz.fundoo.*.*.*.*(..)) and args(token,*)")

//@Before(value = "execution(* com.bridgelabz.fundoo.notes.service.NotesServiceImpl.*(..)) and args(token,*)")

// @Before(value = "execution(* com.bridgelabz.fundoo.notes.service.NotesServiceImpl.*(..)) and args(notesDto,token)")
