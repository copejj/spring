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
@EnableWebSecurity	
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
                .requestMatchers("/", "/login", "/about", "/css/**", "/images/**", "/favicon.ico").permitAll() // Public paths
                .anyRequest().authenticated() // Everything else is locked
            )
            .formLogin(form -> form
                .loginPage("/login") // Your custom login page URL
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
