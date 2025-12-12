package org.example.search_advance.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String identifier) {
        super("User with identifier '" + identifier + "' already exists.");
    }
}