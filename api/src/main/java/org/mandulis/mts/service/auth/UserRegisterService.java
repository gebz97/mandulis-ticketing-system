package org.mandulis.mts.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.dto.request.RegistrationRequest;
import org.mandulis.mts.dto.response.ErrorMessages;
import org.mandulis.mts.dto.response.UserResponse;
import org.mandulis.mts.entity.Group;
import org.mandulis.mts.entity.User;
import org.mandulis.mts.enums.Role;
import org.mandulis.mts.exception.UserAlreadyExistsException;
import org.mandulis.mts.repository.UserRepository;
import org.mandulis.mts.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * <p>This method registers new users onto the system </p>
     *
     * @param userRegisterRequest the minimum details a user should provide during registration
     * @return the details of the registered user, if successful
     *
     * @since 1.0
     */
    @Transactional
    public UserResponse registerUser(RegistrationRequest userRegisterRequest) {
        if (userRepository.existsByEmail(userRegisterRequest.getEmail()) ||
                userRepository.existsByUsername(userRegisterRequest.getUsername())) {
            throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(userRegisterRequest.getEmail())
                .firstName(userRegisterRequest.getFirstName())
                .lastName(userRegisterRequest.getLastName())
                .username(userRegisterRequest.getUsername())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(user);

        return UserService.convertEntityToUserResponseDto(savedUser);
    }

    /*
    private static UserResponse createUserAndReturnUserResponse(User savedUser) {
        //List<Group> groups = (savedUser.getGroups() == null || savedUser.getGroups().isEmpty()) ?
        //        Collections.emptyList() : savedUser.getGroups();

        /*
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                groups,
                savedUser.getRole(),
                savedUser.getEmail()
        );
        return UserService.convertEntityToUserResponseDto(savedUser);
    }
    */
}
