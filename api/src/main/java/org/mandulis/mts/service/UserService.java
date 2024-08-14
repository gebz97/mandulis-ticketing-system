package org.mandulis.mts.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.entity.Group;
import org.mandulis.mts.entity.User;
import org.mandulis.mts.enums.Role;
import org.mandulis.mts.repository.UserRepository;
import org.mandulis.mts.dto.request.RegistrationRequest;
import org.mandulis.mts.dto.response.UserResponse;
import org.mandulis.mts.exception.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserResponse convertEntityToUserResponseDto(User user) {
        UserResponse foundUser =  new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getGroups(),
                user.getRole(),
                user.getEmail()
        );

        if (user.getRole() != null) {
            foundUser.setRole(user.getRole());
        } else {
            foundUser.setRole(Role.USER);
        }

        return foundUser;
    }

    @Transactional
    public Optional<UserResponse> findUserResponseById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.map(this::convertEntityToUserResponseDto);
    }

    public Optional<UserResponse> findUserResponseByUsername(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        return foundUser.map(this::convertEntityToUserResponseDto);
    }
}
