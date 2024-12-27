package org.mandulis.mts.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mandulis.mts.exception.UserNotFoundException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mandulis.mts.rest.ErrorMessages.USER_NOT_FOUND;
import static org.mandulis.mts.user.UserHelperFactory.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Nested
    class FindAllUsers {
        @Test
        void should_find_all_users_with_no_role_and_no_group() {
            List<User> users = List.of(
                    UserHelperFactory.userWithNoRoleAndNoGroups()
            );
            when(userRepository.findAll()).thenReturn(users);

            List<UserResponse> results = userService.findAllUsers();

            assertThat(results).hasSize(1);
            UserResponse user = results.getFirst();
            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getUsername()).isEqualTo("username");
            assertThat(user.getFirstName()).isEqualTo("firstname");
            assertThat(user.getLastName()).isEqualTo("lastname");
            assertThat(user.getEmail()).isEqualTo("example@gmail.com");
            assertThat(user.getRole()).isEqualTo(Role.USER);
            assertThat(user.getGroups()).isEmpty();
        }

        @Test
        void should_find_all_users_with_role_and_no_group() {
            List<User> users = List.of(
                    UserHelperFactory.userWithAdminRoleAndNoGroups()
            );
            when(userRepository.findAll()).thenReturn(users);

            List<UserResponse> results = userService.findAllUsers();

            assertThat(results).hasSize(1);
            UserResponse user = results.getFirst();
            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getUsername()).isEqualTo("username");
            assertThat(user.getFirstName()).isEqualTo("firstname");
            assertThat(user.getLastName()).isEqualTo("lastname");
            assertThat(user.getEmail()).isEqualTo("example@gmail.com");
            assertThat(user.getRole()).isEqualTo(Role.ADMIN);
            assertThat(user.getGroups()).isEmpty();
        }

        @Test
        void should_find_all_users_with_admin_role_and_groups() {
            List<User> users = List.of(
                    userWithAdminRoleAndGroups()
            );
            when(userRepository.findAll()).thenReturn(users);

            List<UserResponse> results = userService.findAllUsers();

            assertThat(results).hasSize(1);
            UserResponse user = results.getFirst();
            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getUsername()).isEqualTo("username");
            assertThat(user.getFirstName()).isEqualTo("firstname");
            assertThat(user.getLastName()).isEqualTo("lastname");
            assertThat(user.getEmail()).isEqualTo("example@gmail.com");
            assertThat(user.getRole()).isEqualTo(Role.ADMIN);
            assertThat(user.getGroups()).hasSize(1);
            UserGroupDetails group = user.getGroups().getFirst();
            assertThat(group.getId()).isEqualTo(2L);
            assertThat(group.getName()).isEqualTo("group name");
            assertThat(group.getDescription()).isEqualTo("group description");
        }
    }

    @Nested
    class DeleteUserById {
        @Test
        void should_throw_user_not_found_exception_when_user_does_not_exists() {
            Long userId = 1L;
            when(userRepository.existsById(1L)).thenReturn(false);

            assertThatThrownBy(() -> userService.deleteUserById(userId))
                    .isExactlyInstanceOf(UserNotFoundException.class)
                    .hasMessage(USER_NOT_FOUND);
        }

        @Test
        void should_delete_user_by_id() {
            Long userId = 1L;
            when(userRepository.existsById(1L)).thenReturn(true);

            userService.deleteUserById(userId);

            verify(userRepository).deleteById(userId);
        }
    }

    @Nested
    class SaveUser {
        @Test
        void should_not_save_user_if_user_email_already_exists() {
            UserRequest userRequest = userRequestWithAdminRole();
            when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(true);

            Optional<UserResponse> result = userService.saveUser(userRequest);

            assertThat(result).isEmpty();
        }

        @Test
        void should_not_save_user_if_user_username_already_exists() {
            UserRequest userRequest = userRequestWithAdminRole();
            when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(true);

            Optional<UserResponse> result = userService.saveUser(userRequest);

            assertThat(result).isEmpty();
        }

        @Test
        void should_save_user_if_user_username_and_email_do_not_already_exist() {
            UserRequest userRequest = userRequestWithAdminRole();
            when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);
            when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);
            when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("password");
            User expectedUser = User.builder()
                    .email(userRequest.getEmail())
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .username(userRequest.getUsername())
                    .password("password")
                    .role(userRequest.getRole())
                    .build();
            when(userRepository.save(any(User.class))).thenReturn(expectedUser);

            Optional<UserResponse> result = userService.saveUser(userRequest);

            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(expectedUser.getId());
            assertThat(result.get().getFirstName()).isEqualTo(expectedUser.getFirstName());
            assertThat(result.get().getLastName()).isEqualTo(expectedUser.getLastName());
            assertThat(result.get().getUsername()).isEqualTo(expectedUser.getUsername());
            assertThat(result.get().getRole()).isEqualTo(expectedUser.getRole());
        }
    }

    @Nested
    class UpdateUser {

        public static final String NEW_EMAIL = "new-email@mail.com";
        public static final String NEW_USERNAME = "new-username";

        @Test
        void should_not_update_user_if_updated_email_already_exists() {
            Long userId = 1L;
            User existingUser = userWithAdminRoleAndGroups();
            UserRequest userRequest = userRequestWithAdminRoleAndNewEmail(NEW_EMAIL);
            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
            when(userRepository.existsByEmail(NEW_EMAIL)).thenReturn(true);

            Optional<UserResponse> result = userService.updateUserById(userId, userRequest);

            assertThat(result).isEmpty();
        }

        @Test
        void should_not_update_user_if_updated_username_already_exists() {
            Long userId = 1L;
            User existingUser = userWithAdminRoleAndGroups();
            UserRequest userRequest = userRequestWithAdminRoleAndNewEmailAndNewUsername(NEW_EMAIL, NEW_USERNAME);
            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
            when(userRepository.existsByEmail(NEW_EMAIL)).thenReturn(false);
            when(userRepository.existsByUsername(NEW_USERNAME)).thenReturn(true);

            Optional<UserResponse> result = userService.updateUserById(userId, userRequest);

            assertThat(result).isEmpty();
        }

        @Test
        void should_update_user_if_updated_email_and_username_do_not_already_exist() {
            Long userId = 1L;
            User existingUser = userWithAdminRoleAndGroups();
            UserRequest updateUserRequest =
                    new UserRequest(
                            "my-new-username",
                            "new-firstname",
                            "new-lastname",
                            "new-email@gmail.com",
                            "new-password",
                            Role.USER
                    );
            User updatedUser = User.builder()
                            .id(existingUser.getId())
                            .username(updateUserRequest.getUsername())
                            .firstName(updateUserRequest.getFirstName())
                            .lastName(updateUserRequest.getLastName())
                            .email(updateUserRequest.getEmail())
                            .password(updateUserRequest.getPassword())
                            .role(updateUserRequest.getRole())
                            .build();
            when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
            when(userRepository.existsByEmail(updateUserRequest.getEmail())).thenReturn(false);
            when(userRepository.existsByUsername(updateUserRequest.getUsername())).thenReturn(false);

            when(userRepository.save(any(User.class))).thenReturn(updatedUser);

            Optional<UserResponse> result = userService.updateUserById(userId, updateUserRequest);

            assertThat(result).isPresent();
            UserResponse user = result.get();
            assertThat(user.getId()).isEqualTo(existingUser.getId());
            assertThat(user.getFirstName()).isEqualTo(existingUser.getFirstName());
            assertThat(user.getLastName()).isEqualTo(existingUser.getLastName());
            assertThat(user.getRole()).isEqualTo(existingUser.getRole());
            assertThat(user.getEmail()).isEqualTo(existingUser.getEmail());
            assertThat(user.getUsername()).isEqualTo(existingUser.getUsername());
        }
    }
}
