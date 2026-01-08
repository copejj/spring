package com.braindribbler.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

	@GetMapping("/hello")
	public String sayHello(@RequestParam(value = "name", defaultValue = "Hunnie") String name) {
		return String.format("Hello, %s!", name);
	}

	@GetMapping
	public String greet() {
		return "Welcome to the Spring Boot Application!";
	}
}