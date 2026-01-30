package com.braindribbler.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RemoteAccessService {
	
	private final String apiBaseUrl;

	public RemoteAccessService(@Value("${api.base-url}") String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public void callApi() {
		// Implementation for calling the remote API using the base URL
		System.out.println("Calling API at: " + apiBaseUrl);
		// Add your API call logic here
	}
}
