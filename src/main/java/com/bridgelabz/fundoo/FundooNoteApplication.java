package com.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class FundooNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooNoteApplication.class, args);
	}

}
