package org.mandulis.mts.dto.response;

public class ErrorMessages {

    private ErrorMessages(){}

    public static final String USER_NOT_FOUND = "User not found with username: ";
    public static final String LOGIN_USERNAME_VALIDATION = "Username must be between 3 and 20 characters";
    public static final String PASSWORD_MANDATORY = "Password is mandatory";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String USER_ALREADY_EXISTS = "User already exists with this username or email";
    public static final String USER_UNIQUE_VALUES_VALIDATION = "Username and email must be unique";
}
