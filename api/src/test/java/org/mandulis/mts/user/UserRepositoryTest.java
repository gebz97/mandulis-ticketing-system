package org.mandulis.mts.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mandulis.mts.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserHelperFactory.userWithNoRoleAndNoGroups());
    }

    @Nested
    class FindByUsername {

        @Test
        void UserRepository_ShouldReturnUserWithNoRoleAndNoGroups() {
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
        void UserRepository_ShouldReturnEmptyIfUsernameDoesNotExist() {
            Optional<User> optionalUser = userRepository.findByUsername("Random User");

            assertTrue(optionalUser.isEmpty());
        }

    }

    @Nested
    class FindByEmail {

        @Test
        void UserRepository_ShouldReturnUserWithNoRoleAndGroups() {
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
        void UserRepository_ShouldReturnEmptyIfEmailDoesNotExist() {
            Optional<User> optionalUser = userRepository.findByEmail("Random email!");

            assertTrue(optionalUser.isEmpty());
        }

    }

    @Nested
    class FindById {

        @Test
        void UserRepository_ShouldReturnUserWithNoRoleAndGroups() {
            Optional<User> optionalUser = userRepository.findById(user.getId());

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
        void UserRepository_ShouldReturnEmptyIfEmailDoesNotExist() {
            Optional<User> optionalUser = userRepository.findById(99L);

            assertTrue(optionalUser.isEmpty());
        }

    }

    @Nested
    class FindByUsernameOrEmail {

        @Test
        void UserRepository_ShouldReturnUserIfEmailExistsAndUsernameDoesNot() {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail("stohirov", user.getEmail());

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
        void UserRepository_ShouldReturnUserIfUsernameExistsAndEmailDoesNot() {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail(user.getUsername(), "hello@gmail.com");

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
        void UserRepository_ShouldReturnUserIfUsernameAndEmailExists() {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

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
        void UserRepository_ShouldReturnEmptyIfUsernameAndEmailDoesNotExist() {
            Optional<User> optionalUser = userRepository.findByUsernameOrEmail("2", "s");
            assertTrue(optionalUser.isEmpty());
        }

    }

    @Nested
    class ExistsByEmail {

        @Test
        void UserRepository_ShouldReturnTrueIfEmailExists() {
            boolean exists = userRepository.existsByEmail(user.getEmail());
            assertTrue(exists);
        }

        @Test
        void UserRepository_ShouldReturnFalseIfEmailDoesNotExist() {
            boolean exists = userRepository.existsByEmail("@gmail.com");
            assertFalse(exists);
        }

    }

    @Nested
    class ExistsByUsername {

        @Test
        void UserRepository_ShouldReturnTrueIfUsernameExists() {
            boolean exists = userRepository.existsByUsername(user.getUsername());
            assertTrue(exists);
        }

        @Test
        void UserRepository_ShouldReturnFalseIfUsernameDoesNotExist() {
            boolean exists = userRepository.existsByUsername("@gmail.com");
            assertFalse(exists);
        }

    }

}