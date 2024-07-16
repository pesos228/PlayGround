package org.community.exceptions;

public class DialogNotFoundException extends RuntimeException{
    public DialogNotFoundException(int id) {
        super("Dialog with user with id " + id + " doesn't exists");
    }
}
