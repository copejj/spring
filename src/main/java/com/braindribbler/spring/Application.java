package com.braindribbler.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Starting BrainDribbler Spring Application...");
		System.out.println("Java Version: " + System.getProperty("java.version"));
		System.out.println("DB_USER: " + System.getProperty("spring.datasource.username"));
		SpringApplication.run(Application.class, args);
	}

}
