package org.mandulis.mts.rest;

public class ErrorMessages {

    public static final String USER_NOT_FOUND = "User not found with username: ";
    public static final String LOGIN_USERNAME_VALIDATION = "Username must be between 3 and 20 characters";
    public static final String PASSWORD_MANDATORY = "Password is mandatory";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String USER_ALREADY_EXISTS = "User already exists with this username or email";
    public static final String USER_UNIQUE_VALUES_VALIDATION = "Username and email must be unique";
    public static final String USERNAME_ALREADY_EXISTS = "User already exists with this username";
    public static final String USER_ALREADY_EXISTS_WITH_EMAIL = "User already exists with email";
    public static final String NO_GROUPS_FOUND = "No groups found";
    public static final String INVALID_TAG = "Invalid tag";
    public static final String INVALID_USER = "Invalid User";
    public static final String INVALID_ATTACHMENT_ID = "Attachment ID is not valid";

    private ErrorMessages(){}
}
