package org.community.exceptions;

public class DiscussionNotOwnedException extends RuntimeException{
    public DiscussionNotOwnedException(String message) {
        super(message);
    }
}
