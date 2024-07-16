package org.community.exceptions;

public class UserAlreadyFriendsException extends RuntimeException {
    public UserAlreadyFriendsException(String message) {
        super(message);
    }
}