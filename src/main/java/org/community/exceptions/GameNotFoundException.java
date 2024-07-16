package org.community.exceptions;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(String name) {
        super("Game "+ name +" not found");
    }
}
