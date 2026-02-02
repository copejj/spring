package com.braindribbler.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.braindribbler.spring.models.users.User;
import com.braindribbler.spring.repositories.users.UserRepository;
import com.braindribbler.spring.security.DribblerUserDetails;

@Service
public class DribblerUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Fetch user from the database
		// Return the security wrapper
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return new DribblerUserDetails(user);
	}
}	