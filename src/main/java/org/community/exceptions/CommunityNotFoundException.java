package org.community.exceptions;

public class CommunityNotFoundException extends RuntimeException{
    public CommunityNotFoundException(String name) {
        super("Community "+ name+ " not found");
    }
}
