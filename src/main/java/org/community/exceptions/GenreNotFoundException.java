package org.community.exceptions;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(String name) {
        super("Genre with "+ name +" not found");
    }
}
