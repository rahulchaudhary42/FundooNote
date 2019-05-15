package com.bridgelabz.fundoo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundoo.response.Response;
@Configuration
public class ApplicationConfiguration {
	
	// Creating bean object for ModelMapper
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	// Creating bean object for PasswordEncoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Creating bean object for Response
	@Bean
	public Response getResponse() {
		return new Response();
	}

}
