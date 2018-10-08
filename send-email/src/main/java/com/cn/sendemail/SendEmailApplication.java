package com.cn.sendemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SendEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendEmailApplication.class, args);
	}
}
