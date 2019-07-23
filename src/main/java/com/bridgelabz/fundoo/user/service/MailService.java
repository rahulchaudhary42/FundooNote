package com.bridgelabz.fundoo.user.service;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.user.model.Email;
 
import com.bridgelabz.fundoo.util.JWTToken;
 

@Component
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;	
	
//	@Autowired
//	private UserToken userToken;
	
	@Autowired
	//private AmqpTemplate rabbitTemplate;

	@Value("${fundoo.rabbitmq.exchange}")
	private String exchange;

	@Value("${fundoo.rabbitmq.routingkey}")
	private String routingkey;
	
	@Autowired
	RabbitService service;
	
	@Autowired
	JWTToken jWTToken;
	
//	@RabbitListener(queues = "${fundoo.rabbitmq.queue}")
	public void send(Email email) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(email.getTo()); 
	    message.setSubject(email.getSubject()); 
	    message.setText(email.getBody());
	    //rabbitTemplate.convertAndSend(exchange, routingkey,email);
	    //System.out.println("Sending Email ");
	    try {
		
	    	service.send(message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    
	    javaMailSender.send(message);

	    System.out.println("Email Sent Successfully!!");

	}
	
	public String getLink(String link,String id) throws IllegalArgumentException, UnsupportedEncodingException 
	{
		return link+jWTToken.generateToken(id);
	}

//	public void rabitsender(Email email) {
//		rabbitTemplate.convertAndSend(exchange, routingkey, email);
//	}
}
