package org.community.exceptions;

public class DiscussionClosedException extends RuntimeException {
    public DiscussionClosedException() {
        super("Discussion is closed");
    }
}