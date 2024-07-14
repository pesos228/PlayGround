package org.community.service;

import org.community.entities.Game;
import org.community.entities.Genre;
import org.community.entities.User;
import org.community.repository.impl.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepositoryImpl userRepository;

    @Autowired
    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public void save(User user){
        User userTest = userRepository.findByEmail(user.getEmail());
        if (userTest == null){
            userRepository.save(user);
        }else{
            throw new IllegalArgumentException("User with "+ user.getEmail() +" mail already exists");
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
            throw new IllegalArgumentException("One or both users not found");
        }
        if (isFriends(userId, friendId)) {
            throw new RuntimeException("Users are already friends");
        }

        user.addFriend(friend);
        userRepository.save(user);
    }

    @Transactional
    public void removeFriend(int userId, int friendId){
        User user = userRepository.findById(userId);
        User friend = userRepository.findById(friendId);

        if (user == null || friend == null) {
            throw new IllegalArgumentException("One or both users not found");
        }
        if (!userRepository.existsFriendByUserIdAndFriendId(userId, friendId)) {
            throw new RuntimeException("Users are not friends");
        }

        user.removeFriend(friend);
        userRepository.save(user);
    }

    public boolean isFriends(int userId1, int userId2){
        return userRepository.existsFriendByUserIdAndFriendId(userId1, userId2) && userRepository.existsFriendByUserIdAndFriendId(userId2, userId1);
    }

    public List<User> findAllFriends(int userId){
        return userRepository.findFriendsByUserId(userId);
    }

    public List<Game> findAllGames(int userId){
        return userRepository.findGamesByUserId(userId);
    }

    public boolean existsGameByUserIdAndGameId(int userId, int gameId){
        return userRepository.existsGameByUserIdAndGameId(userId, gameId);
    }

    public User findById(int id){
        return userRepository.findById(id);
    }
}
