package com.braindribbler.spring.service.users.impl;

import com.braindribbler.spring.dto.users.UserDTO;
import com.braindribbler.spring.dto.users.PasswordDTO;
import com.braindribbler.spring.models.users.User;
import com.braindribbler.spring.repositories.users.UserRepository;
import com.braindribbler.spring.service.users.UserService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserDtoById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getUserId(), 
			user.getUserName(), 
			user.getEmail(),
			user.getFirstName(),
			user.getLastName(), 
            PasswordDTO.empty()
        );
    }

    @Override
    @Transactional
    public void updateUser(UserDTO dto) {
        User user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setUserName(dto.userName());
        user.setEmail(dto.email());
		user.setFirstName(dto.firstName());
		user.setLastName(dto.lastName());

        String newPass = dto.passwordData().newPassword();
        if (newPass != null && !newPass.isEmpty()) {
            // Hashing happens here in the Service Layer
            user.setPassword(passwordEncoder.encode(newPass));
        }

        userRepository.save(user);
    }

	@Override
	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
