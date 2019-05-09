package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.model.Email;
import com.bridgelabz.fundoo.util.UserToken;

@Component
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;	
	
	@Autowired
	private UserToken userToken;
	
	public void send(Email email) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(email.getTo()); 
	    message.setSubject(email.getSubject()); 
	    message.setText(email.getBody());
	    
	    System.out.println("Sending Email ");
	    
	    javaMailSender.send(message);

	    System.out.println("Email Sent Successfully!!");

	}
	
	public String getLink(String link,String id) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		return link+userToken.generateToken(id);
	}

}
