package org.mandulis.mts.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.mandulis.mts.group.Group;

import java.util.Collections;
import java.util.List;

public class UserHelperFactory {
    private static Long id = 1L;
    private static String username = "username";
    private static String firstname = "firstname";
    private static String lastname = "lastname";
    private static String email = "example@gmail.com";
    private static Role adminRole = Role.ADMIN;
    private static Long groupId = 2L;
    private static String groupName = "group name";
    private static String groupDescription = "group description";
    private static List<Group> groups = List.of(
            new Group(groupId, groupName, groupDescription, Collections.emptyList(), Collections.emptyList())
    );
    private static String password = "password";

    public static User userWithNoRoleAndNoGroups() {
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .role(null)
                .groups(null)
                .build();
    }

    public static User userWithAdminRoleAndNoGroups() {
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .role(adminRole)
                .groups(null)
                .build();
    }

    public static User userWithAdminRoleAndGroups() {
        return User.builder()
                .id(id)
                .username(username)
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .role(adminRole)
                .groups(groups)
                .build();
    }

    public static UserRequest userRequestWithAdminRole() {
        return new UserRequest(username, firstname, lastname, email, password, adminRole);
    }

    public static UserRequest userRequestWithAdminRoleAndNewEmail(String newEmail) {
        return new UserRequest(username, firstname, lastname, newEmail, password, adminRole);
    }

    public static UserRequest userRequestWithAdminRoleAndNewEmailAndNewUsername(String newEmail, String newUsername) {
        return new UserRequest(newUsername, firstname, lastname, newEmail, password, adminRole);
    }
}
