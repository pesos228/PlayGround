package org.community.controller;

import org.community.dto.GameDtoName;
import org.community.dto.UserDtoFriendsList;
import org.community.dto.UserDtoList;
import org.community.dto.UserDtoRegister;
import org.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody UserDtoRegister userDtoRegister){
        userService.save(userDtoRegister);
    }

    @GetMapping
    public List<UserDtoList> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}/friends")
    public List<UserDtoFriendsList> getAllFriends(@PathVariable int userId) {
        return userService.findAllFriends(userId);
    }

    @PostMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void removeFriend(@PathVariable int userId, @PathVariable int friendId) {
        userService.removeFriend(userId, friendId);
    }

    @GetMapping("/{userId}/games")
    public List<GameDtoName> getAllGames(@PathVariable int userId) {
        return userService.findAllGames(userId);
    }
}
