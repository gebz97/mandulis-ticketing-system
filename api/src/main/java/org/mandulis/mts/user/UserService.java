package org.mandulis.mts.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.group.GroupService;
import org.mandulis.mts.rest.ErrorMessages;
import org.mandulis.mts.exception.UserNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mandulis.mts.user.UserSpecification.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static UserResponse convertEntityToUserResponseDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole() : Role.USER)
                .groups(
                        user.getGroups() != null ?
                                user.getGroups()
                                        .stream()
                                        .map(GroupService::convertEntityToUserGroupDetailsDto)
                                        .toList()
                                : new ArrayList<>()
                )
                .build();
    }

    @Transactional
    public Optional<UserResponse> findUserResponseById(Long id) {
        return findUserById(id).map(UserService::convertEntityToUserResponseDto);
    }

    @Transactional
    public Optional<UserResponse> findUserResponseByUsername(String username) {
        return findUserByUsername(username).map(UserService::convertEntityToUserResponseDto);
    }

    @Transactional
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserService::convertEntityToUserResponseDto)
                .toList();
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    public Optional<UserResponse> saveUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername()))
        {
            return Optional.empty();
        }
        User user = createUserFromRequest(request);
        User savedUser = userRepository.save(user);
        return Optional.of(convertEntityToUserResponseDto(savedUser));
    }

    @Transactional
    public Optional<UserResponse> updateUserById(Long id, UserRequest request) {
        return findExistingUser(id)
                .flatMap(user -> validateAndUpdateUser(user, request))
                .map(user -> convertEntityToUserResponseDto(userRepository.save(user)));
    }

    @Transactional
    public List<UserResponse> searchUsers(String username, String firstName, String lastName, String email) {
        Specification<User> filters = Specification.where(hasUsernameLike(username))
                .or(hasFirstNameLike(firstName))
                .or(hasLastNameLike(lastName))
                .or(hasEmailLike(email));
        List<User> users = userRepository.findAll(filters);
        return users.isEmpty() ? new ArrayList<>() : users.stream().map(UserService::convertEntityToUserResponseDto).toList();
    }

    private Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    private Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private Optional<User> findExistingUser(Long id) {
        return findUserById(id);
    }

    private Optional<User> validateAndUpdateUser(User user, UserRequest request) {
        if (isEmailChangedAndExists(user, request.getEmail()) || isUsernameChangedAndExists(user, request.getUsername())) {
            return Optional.empty();
        }
        updateUserFields(user, request);
        return Optional.of(user);
    }

    private boolean isEmailChangedAndExists(User user, String newEmail) {
        return !user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail);
    }

    private boolean isUsernameChangedAndExists(User user, String newUsername) {
        return !user.getUsername().equals(newUsername) && userRepository.existsByUsername(newUsername);
    }

    private void updateUserFields(User user, UserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    private User createUserFromRequest(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
    }
}
