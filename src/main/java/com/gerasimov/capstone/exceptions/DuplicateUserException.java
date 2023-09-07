package com.gerasimov.capstone.exceptions;

public class DuplicateUserException extends IllegalArgumentException {
    public DuplicateUserException(String email) {
        super("User with email address '" + email + "' already exists in the database.");
    }
}
