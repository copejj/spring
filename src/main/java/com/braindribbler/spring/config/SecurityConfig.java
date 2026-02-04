package com.braindribbler.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug=true)	
@EnableMethodSecurity(securedEnabled=true)
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
                // 1. Explicitly permit the login page and static assets
                .requestMatchers("/", "/login", "/about", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll() 
                // 2. Ensure the "error" and "logout" parameters aren't blocked
                .requestMatchers("/login?error", "/login?logout").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Your custom login page URL
                .loginProcessingUrl("/login") // URL to submit the username and password
                .defaultSuccessUrl("/", true) // Redirect after login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
		
		return http.build();
	}	
}
