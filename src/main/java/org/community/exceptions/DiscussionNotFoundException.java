package org.community.exceptions;

public class DiscussionNotFoundException extends RuntimeException{
    public DiscussionNotFoundException(int id) {
        super("A Discussion with ID " + id + " not found");
    }
}
