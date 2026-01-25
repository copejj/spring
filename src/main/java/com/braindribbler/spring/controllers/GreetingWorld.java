package com.braindribbler.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingWorld {

	@GetMapping("/hello")
	public String sayHello(@RequestParam(value = "name", defaultValue = "Hunnie") String name) {
		return String.format("Hello, %s!", name);
	}

	@GetMapping("/welcome")
	public String greet() {
		return "Welcome to the Spring Boot Application!";
	}

	@GetMapping("/goodbye")
	public String sayGoodbye(@RequestParam(value = "name", defaultValue = "Zippo") String name) {
		return String.format("Goodbye, %s!", name);
	}
}