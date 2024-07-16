package org.community.exceptions.advice;

import org.community.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String userAlreadyExistsHandler(UserAlreadyExistsException e){
        return e.getMessage();
    }
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFound(UserNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyFriendsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUserAlreadyFriends(UserAlreadyFriendsException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UsersNotFriendsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUsersNotFriends(UsersNotFriendsException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DialogNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDialogNotFound(DialogNotFoundException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(GenreAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleGenreAlreadyExists(GenreAlreadyExistsException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(GenreNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGenreNotFound(GenreNotFoundException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(GameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleGameAlreadyExists(GameAlreadyExistsException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(FeedbackNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFeedbackNotFound(FeedbackNotFoundException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(FeedbackAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFeedbackAlreadyExists(FeedbackAlreadyExistsException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(GameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGameNotFound(GameNotFoundException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(CommunityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCommunityNotFound(CommunityNotFoundException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(DiscussionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDiscussionNotFound(DiscussionNotFoundException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(DiscussionNotOwnedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleDiscussionNotOwned(DiscussionNotOwnedException e){return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(DiscussionClosedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDiscussionClosed(DiscussionClosedException e){return e.getMessage();}
}
