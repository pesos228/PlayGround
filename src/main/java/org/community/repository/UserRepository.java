package org.community.repository;

import org.community.entities.Game;
import org.community.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> findFriendsByUserId(int id);
    boolean existsFriendByUserIdAndFriendId(int userId, int friendId);
    List<Game> findGamesByUserId(int id);
    boolean existsGameByUserIdAndGameId(int userId, int gameId);
    User findByEmail(String email);
    void deleteById(int id);
    void save(User user);
    User findById(int id);
    List<User> findAll();
}
