package org.community.service;

import org.community.dto.UserDtoFriendsList;
import org.community.dto.GameDtoName;
import org.community.dto.UserDtoList;
import org.community.dto.UserDtoRegister;
import org.community.entities.User;
import org.community.exceptions.UserAlreadyExistsException;
import org.community.exceptions.UserAlreadyFriendsException;
import org.community.exceptions.UserNotFoundException;
import org.community.exceptions.UsersNotFriendsException;

import java.util.List;

public interface UserService {

    void save(UserDtoRegister userDtoRegister) throws UserAlreadyExistsException;


    void addFriend(int userId, int friendId) throws UserNotFoundException, UserAlreadyFriendsException;

    void removeFriend(int userId, int friendId) throws UserNotFoundException, UsersNotFriendsException;

    boolean isFriends(int userId1, int userId2);

    List<UserDtoFriendsList> findAllFriends(int userId);

    List<GameDtoName> findAllGames(int userId);

    boolean existsGameByUserIdAndGameId(int userId, int gameId);

    User findById(int id);

    User findByEmail(String email);

    List<UserDtoList> findAllUsers();
}
