package org.community.service;

import org.community.dto.UserDtoFriendsList;
import org.community.dto.GameDtoName;
import org.community.dto.UserDtoList;
import org.community.dto.UserDtoRegister;
import org.community.entities.Game;
import org.community.entities.User;
import org.community.exceptions.UserAlreadyExistsException;
import org.community.exceptions.UserAlreadyFriendsException;
import org.community.exceptions.UserNotFoundException;
import org.community.exceptions.UsersNotFriendsException;
import org.community.repository.impl.UserRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService {

    private final UserRepositoryImpl userRepository;

    @Autowired
    public UserService(UserRepositoryImpl userRepository, ModelMapper modelMapper) {
        super(modelMapper);
        this.userRepository = userRepository;
    }


    @Transactional
    public void save(UserDtoRegister userDtoRegister){
        User user = convertToEntity(userDtoRegister, User.class);
        User userTest = userRepository.findByEmail(user.getEmail());
        if (userTest == null){
            user.setRegTime(LocalDateTime.now());
            userRepository.save(user);
        }else{
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    public void deleteById(int id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void addFriend(int userId, int friendId){
        User user = userRepository.findById(userId);
        User friend = userRepository.findById(friendId);
        if (user == null || friend == null) {
            throw new UserNotFoundException("One or both users not found");
        }
        if (userRepository.existsFriendByUserIdAndFriendId(userId, friendId)){
            throw new UserAlreadyFriendsException("The friend request has already been submitted");
        }
        if (isFriends(userId, friendId)) {
            throw new UserAlreadyFriendsException("Users are already friends");
        }

        user.addFriend(friend);
        userRepository.save(user);
    }

    @Transactional
    public void removeFriend(int userId, int friendId){
        User user = userRepository.findById(userId);
        User friend = userRepository.findById(friendId);

        if (user == null || friend == null) {
            throw new UserNotFoundException("One or both users not found");
        }
        if (!userRepository.existsFriendByUserIdAndFriendId(userId, friendId)) {
            throw new UsersNotFriendsException("Users are not friends");
        }

        user.removeFriend(friend);
        userRepository.save(user);
    }

    public boolean isFriends(int userId1, int userId2){
        return userRepository.existsFriendByUserIdAndFriendId(userId1, userId2) && userRepository.existsFriendByUserIdAndFriendId(userId2, userId1);
    }

    public List<UserDtoFriendsList> findAllFriends(int userId) {
        List<User> friends = userRepository.findFriendsByUserId(userId);
        return friends.stream()
                .map(friend -> convertToDto(friend, UserDtoFriendsList.class))
                .collect(Collectors.toList());
    }

    public List<GameDtoName> findAllGames(int userId){
        List<Game> games = userRepository.findGamesByUserId(userId);
        return games.stream()
                .map(game -> convertToDto(game, GameDtoName.class))
                .collect(Collectors.toList());
    }

    public boolean existsGameByUserIdAndGameId(int userId, int gameId){
        return userRepository.existsGameByUserIdAndGameId(userId, gameId);
    }

    public User findById(int id){
        return userRepository.findById(id);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<UserDtoList> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDtoList userDtoList = convertToDto(user, UserDtoList.class);
                    userDtoList.setFriendsCount(user.getListFriends().size());
                    return userDtoList;
                })
                .collect(Collectors.toList());
    }
}
