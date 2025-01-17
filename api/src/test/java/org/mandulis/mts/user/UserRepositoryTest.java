package org.mandulis.mts.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Nested
    class FindByUsername {

        @Test
        void UserRepository_ShouldReturnUserWithNoRoleAndGroups() {
            User user = UserHelperFactory.userWithNoRoleAndNoGroups();

            userRepository.save(user);

            Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

            assertTrue(optionalUser.isPresent());
            User savedUser = optionalUser.get();

            assertNotNull(savedUser.getUsername());
            assertNotNull(savedUser.getId());
            assertNull(savedUser.getGroups());
            assertNull(savedUser.getRole());
            assertEquals(savedUser.getUsername(), user.getUsername());
            assertEquals(savedUser.getEmail(), user.getEmail());
            assertEquals(savedUser.getFirstName(), user.getFirstName());
            assertEquals(savedUser.getLastName(), user.getLastName());
        }

        @Test
        void UserRepository_ShouldReturnUserWithAdminRoleAndNoGroups() {
            User user = UserHelperFactory.userWithAdminRoleAndNoGroups();

            userRepository.save(user);

            Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

            assertTrue(optionalUser.isPresent());
            User savedUser = optionalUser.get();

            assertNotNull(savedUser.getUsername());
            assertNotNull(savedUser.getId());
            assertEquals(Role.ADMIN, savedUser.getRole());
            assertNull(savedUser.getGroups());
            assertEquals(savedUser.getUsername(), user.getUsername());
            assertEquals(savedUser.getEmail(), user.getEmail());
            assertEquals(savedUser.getFirstName(), user.getFirstName());
            assertEquals(savedUser.getLastName(), user.getLastName());
        }

        @Test
        void UserRepository_ShouldReturnEmptyIfUsernameDoesNotExist() {
            Optional<User> optionalUser = userRepository.findByUsername("Random User");

            assertTrue(optionalUser.isEmpty());
        }

    }

    @Nested
    class FindByEmail {

        @Test
        void UserRepository_ShouldReturnUserWithNoRoleAndGroups() {
            User user = UserHelperFactory.userWithNoRoleAndNoGroups();

            userRepository.save(user);

            Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

            assertTrue(optionalUser.isPresent());
            User savedUser = optionalUser.get();

            assertNotNull(savedUser.getUsername());
            assertNotNull(savedUser.getId());
            assertNull(savedUser.getGroups());
            assertNull(savedUser.getRole());
            assertEquals(savedUser.getUsername(), user.getUsername());
            assertEquals(savedUser.getEmail(), user.getEmail());
            assertEquals(savedUser.getFirstName(), user.getFirstName());
            assertEquals(savedUser.getLastName(), user.getLastName());
        }

        @Test
        void UserRepository_ShouldReturnUserWithAdminRoleAndNoGroups() {
            User user = UserHelperFactory.userWithAdminRoleAndNoGroups();

            userRepository.save(user);

            Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

            assertTrue(optionalUser.isPresent());
            User savedUser = optionalUser.get();

            assertNotNull(savedUser.getUsername());
            assertEquals(Role.ADMIN, savedUser.getRole());
            assertNull(savedUser.getGroups());
            assertEquals(savedUser.getUsername(), user.getUsername());
            assertEquals(savedUser.getEmail(), user.getEmail());
            assertEquals(savedUser.getFirstName(), user.getFirstName());
            assertEquals(savedUser.getLastName(), user.getLastName());
            assertEquals(savedUser.getId(), user.getId());
        }

        @Test
        void UserRepository_ShouldReturnEmptyIfEmailDoesNotExist() {
            Optional<User> optionalUser = userRepository.findByEmail("Random email!");

            assertTrue(optionalUser.isEmpty());
        }

    }

    @Nested
    class FindById {
    }

    @Nested
    class FindByUsernameOrEmail {
    }

    @Nested
    class ExistsByEmail {
    }

    @Nested
    class ExistsByUsername {
    }
}