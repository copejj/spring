package com.braindribbler.spring.service.users;

import java.util.List;

import com.braindribbler.spring.dto.users.UserDTO;
import com.braindribbler.spring.forms.users.RegistrationForm;
import com.braindribbler.spring.models.users.User;

public interface UserService {
    UserDTO getUserDtoById(Long userId);
    void updateUser(UserDTO dto, boolean isActualAdmin);
	List<User> getAllUsers();
    User registerNewUser(RegistrationForm form);
    boolean isUserNameAvailable(String userName);
}
