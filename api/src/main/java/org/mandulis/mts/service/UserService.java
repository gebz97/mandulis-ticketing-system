package org.mandulis.mts.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mandulis.mts.dto.request.CreateOrUpdateUserRequest;
import org.mandulis.mts.dto.response.ErrorMessages;
import org.mandulis.mts.entity.User;
import org.mandulis.mts.enums.Role;
import org.mandulis.mts.exception.UserAlreadyExistsException;
import org.mandulis.mts.exception.UserNotFoundException;
import org.mandulis.mts.exception.UserUpdateException;
import org.mandulis.mts.exception.UserValidationException;
import org.mandulis.mts.repository.UserRepository;
import org.mandulis.mts.dto.response.UserResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mandulis.mts.specification.UserSpecification.*;

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
        Optional<User> foundUser = userRepository.findById(id);
        if(foundUser.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
        return foundUser.map(UserService::convertEntityToUserResponseDto);
    }

    @Transactional
    public Optional<UserResponse> findUserResponseByUsername(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
        return foundUser.map(UserService::convertEntityToUserResponseDto);
    }

    @Transactional
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
//        for (User user : users) {
//            userResponses.add(convertEntityToUserResponseDto(user));
//        }
        return users.stream().map(UserService::convertEntityToUserResponseDto).toList();
    }

    public boolean deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
    }

    public UserResponse saveUser(CreateOrUpdateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail()) ||
                userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(ErrorMessages.USER_ALREADY_EXISTS);
        }

        try {
            User user = User.builder()
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();

            User savedUser = userRepository.save(user);
            return convertEntityToUserResponseDto(savedUser);
        } catch (Exception e) {
            throw new UserValidationException(ErrorMessages.USER_UNIQUE_VALUES_VALIDATION);
        }
    }

    @Transactional
    public UserResponse updateUserById(Long id, CreateOrUpdateUserRequest request){
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setRole(request.getRole());

            if(!existingUser.get().getEmail().equals(request.getEmail())
                    && userRepository.existsByEmail(request.getEmail())) {
                throw new UserUpdateException(ErrorMessages.USER_UNIQUE_VALUES_VALIDATION);
            }
            user.setEmail(request.getEmail());

            if(!existingUser.get().getUsername().equals(request.getUsername())
                    && userRepository.existsByUsername(request.getUsername())) {
                throw new UserUpdateException(ErrorMessages.USER_UNIQUE_VALUES_VALIDATION);
            }
            user.setUsername(request.getUsername());

            user.setPassword(passwordEncoder.encode(request.getPassword()));
            return convertEntityToUserResponseDto(userRepository.save(user));
        } else {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
    }

    @Transactional
    public Object searchUsers(String username, String firstName, String lastName, String email) {
        Specification<User> filters = Specification.where(hasUsernameLike(username))
                .or(hasFirstNameLike(firstName))
                .or(hasLastNameLike(lastName))
                .or(hasEmailLike(email));
        List<User> users = userRepository.findAll(filters);

        if (users.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }

        return users.stream().map(UserService::convertEntityToUserResponseDto).toList();
    }
}
