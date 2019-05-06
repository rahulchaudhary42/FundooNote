package com.bridgelabz.fundoo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ApplicationConfiguration {
	
	// Creating bean object for ModelMapper
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
