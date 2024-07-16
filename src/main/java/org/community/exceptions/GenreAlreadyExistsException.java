package org.community.exceptions;

public class GenreAlreadyExistsException extends RuntimeException{
    public GenreAlreadyExistsException(String name) {
        super("Genre with "+ name +" already exists");
    }
}
