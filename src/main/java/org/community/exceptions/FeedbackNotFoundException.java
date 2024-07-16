package org.community.exceptions;

public class FeedbackNotFoundException extends RuntimeException{
    public FeedbackNotFoundException(String message) {
        super(message);
    }
}
