package org.community.exceptions;

public class UsersNotFriendsException extends RuntimeException {
    public UsersNotFriendsException(String message) {
        super(message);
    }
}