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
// What I do in a Service class is that I put this annotation @Transactional(readOnly = true) so that every method can read data
// By doing this, it reduce the number of annotations, but do as you think is best / readable for you
// When I need to update data in a method, I add specifically @Transaction annotation to this method
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // In my opinion, this mapping/converting method should be in a specific class called UserMapper (annotated with @Component) and it should be static
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

    // When you know that you'll only read values from db, you should use @Transactional(readOnly=true), so you'll be sure that nothing will change
    @Transactional(readOnly = true)
    public Optional<UserResponse> findUserResponseById(Long id) {
        // You can use var everywhere (except for Function, Supplier..) if you want =)
        var foundUser = userRepository.findById(id);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
        return foundUser.map(UserService::convertEntityToUserResponseDto);

        // It can also be written in a onliner, but if it's more readable your way, do not change
        // return userRepository.findById(id)
        //     .map(UserService::convertEntityToUserResponseDto)
        //     .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<UserResponse> findUserResponseByUsername(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
        return foundUser.map(UserService::convertEntityToUserResponseDto);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        //List<UserResponse> userResponses = new ArrayList<>();
//        for (User user : users) {
//            userResponses.add(convertEntityToUserResponseDto(user));
//        }
        return users.stream().map(UserService::convertEntityToUserResponseDto).toList();
    }

    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }
    }

    public UserResponse saveUser(CreateOrUpdateUserRequest request) {

        // I think this can be done with one method, something like this "userRepository.existsByEmailOrUserName(request.getEmail(), request.getUserName())"
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
        var existingUser = userRepository.findById(id);
        
        // It is recommanded to return as soon as possible
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }

        var user = existingUser.get();
        checkIfCanBeUpdated(user);
        
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return convertEntityToUserResponseDto(userRepository.save(user));
    }

    @Transactional
    public List<UserResponse> searchUsers(String username, String firstName, String lastName, String email) {
        Specification<User> filters = Specification.where(hasUsernameLike(username))
                .or(hasFirstNameLike(firstName))
                .or(hasLastNameLike(lastName))
                .or(hasEmailLike(email));
        List<User> users = userRepository.findAll(filters);

        // it's not necessary
        // if users is empty, it will return an empty list anyway

        return users.stream().map(UserService::convertEntityToUserResponseDto).toList();
    }

    // The idea is to create the smallest possible methods, it is more reabable and maintenable
    // (My naming is terrible, do not hesitate to change)
    @Transactional(readOnly = true)
    void checkIfCanBeUpdated(User user) {
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())
            || !user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) 
            throw new UserUpdateException(ErrorMessages.USER_UNIQUE_VALUES_VALIDATION);
    }

}
