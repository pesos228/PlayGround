package org.community.exceptions;

public class FeedbackAlreadyExistsException extends RuntimeException{
    public FeedbackAlreadyExistsException(String name) {
        super("A feedback of " + name + " already exists");
    }
}
