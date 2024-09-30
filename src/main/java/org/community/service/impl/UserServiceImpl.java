package org.community.service.impl;

import org.community.dto.GameDtoName;
import org.community.dto.UserDtoFriendsList;
import org.community.dto.UserDtoList;
import org.community.dto.UserDtoRegister;
import org.community.entities.Game;
import org.community.entities.User;
import org.community.exceptions.UserAlreadyExistsException;
import org.community.exceptions.UserAlreadyFriendsException;
import org.community.exceptions.UserNotFoundException;
import org.community.exceptions.UsersNotFriendsException;
import org.community.repository.UserRepository;
import org.community.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(UserDtoRegister userDtoRegister){
        User user = modelMapper.map(userDtoRegister, User.class);
        User userTest = userRepository.findByEmail(user.getEmail());
        if (userTest == null){
            user.setRegTime(LocalDateTime.now());
            userRepository.save(user);
        }else{
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    @Override
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

    @Override
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

    @Override
    public boolean isFriends(int userId1, int userId2){
        return userRepository.existsFriendByUserIdAndFriendId(userId1, userId2) && userRepository.existsFriendByUserIdAndFriendId(userId2, userId1);
    }

    @Override
    public List<UserDtoFriendsList> findAllFriends(int userId) {
        List<User> friends = userRepository.findFriendsByUserId(userId);
        return friends.stream()
                .map(friend -> modelMapper.map(friend, UserDtoFriendsList.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GameDtoName> findAllGames(int userId){
        List<Game> games = userRepository.findGamesByUserId(userId);
        return games.stream()
                .map(game -> modelMapper.map(game, GameDtoName.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsGameByUserIdAndGameId(int userId, int gameId){
        return userRepository.existsGameByUserIdAndGameId(userId, gameId);
    }

    @Override
    public User findById(int id){
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDtoList> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDtoList userDtoList = modelMapper.map(user, UserDtoList.class);
                    userDtoList.setFriendsCount(user.getListFriends().size());
                    return userDtoList;
                })
                .collect(Collectors.toList());
    }
}
