package com.braindribbler.spring.service.users.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braindribbler.spring.dto.users.PasswordDTO;
import com.braindribbler.spring.dto.users.UserDTO;
import com.braindribbler.spring.forms.users.RegistrationForm;
import com.braindribbler.spring.models.users.User;
import com.braindribbler.spring.repositories.users.UserRepository;
import com.braindribbler.spring.service.users.UserService;

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
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        return userRepository.findById(userId)
            .map(user -> new UserDTO(user.getUserId(), 
			user.getUserName(), 
			user.getEmail(),
			user.getFirstName(),
			user.getLastName(), 
            PasswordDTO.empty()
        ))
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void updateUser(UserDTO dto) {
        Long userId = dto.userId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        User user = userRepository.findById(userId)
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

    @Override
    public boolean isUserNameAvailable(String userName) {
        return !userRepository.existsByUserName(userName);
    }

    @Override
    public User registerNewUser(RegistrationForm form) {
                // Double check here just in case
        if (!isUserNameAvailable(form.getUserName())) {
            throw new RuntimeException("User name already taken");
        }
        
        User user = new User();
        user.setUserName(form.getUserName());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());

        String newPass = form.getPassword();
        if (newPass != null && !newPass.isEmpty()) {
            // Hashing happens here in the Service Layer
            user.setPassword(passwordEncoder.encode(newPass));
        }

        return userRepository.save(user);
    }
}
