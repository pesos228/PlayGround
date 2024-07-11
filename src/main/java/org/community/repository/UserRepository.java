package org.community.repository;

import org.community.entities.Game;
import org.community.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT f FROM User u JOIN u.listFriends f WHERE u.id = :id")
    List<User> findFriendsByUserId(@Param(value = "id") int id);
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END FROM User u JOIN u.listFriends f WHERE u.id = :userId AND f.id = :friendId")
    boolean existsFriendByUserIdAndFriendId(@Param(value = "userId") int userId, @Param(value = "friendId") int friendId);
    @Query("SELECT g FROM User u JOIN u.listGames g WHERE u.id = :id")
    List<Game> findGamesByUserId(@Param(value = "id") int id);
    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM User u JOIN u.listGames g WHERE u.id = :userId AND g.id = :gameId")
    boolean existsGameByUserIdAndGameId(@Param(value = "userId") int userId, @Param(value = "gameId") int gameId);

}
