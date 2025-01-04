package org.mandulis.mts.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.user.RegistrationRequest;
import org.mandulis.mts.rest.ErrorMessages;
import org.mandulis.mts.user.UserResponse;
import org.mandulis.mts.user.User;
import org.mandulis.mts.user.Role;
import org.mandulis.mts.exception.UserAlreadyExistsException;
import org.mandulis.mts.user.UserRepository;
import org.mandulis.mts.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
