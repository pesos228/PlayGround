package org.community.repository.impl;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.community.entities.Game;
import org.community.entities.User;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl extends AbstractBaseRepository<User, Integer> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public List<User> findFriendsByUserId(int id) {
        TypedQuery<User> query = entityManager.createQuery("SELECT f FROM User u JOIN u.listFriends f WHERE u.id = :id", User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public boolean existsFriendByUserIdAndFriendId(int userId, int friendId) {
        TypedQuery<Boolean> query = entityManager.createQuery("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END FROM User u JOIN u.listFriends f WHERE u.id = :userId AND f.id = :friendId", Boolean.class);
        query.setParameter("userId", userId);
        query.setParameter("friendId", friendId);
        return query.getSingleResult();
    }

    @Override
    public List<Game> findGamesByUserId(int id) {
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM User u JOIN u.listGames g WHERE u.id = :id", Game.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public boolean existsGameByUserIdAndGameId(int userId, int gameId) {
        TypedQuery<Boolean> query = entityManager.createQuery("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM User u JOIN u.listGames g WHERE u.id = :userId AND g.id = :gameId", Boolean.class);
        query.setParameter("userId", userId);
        query.setParameter("gameId", gameId);
        return query.getSingleResult();
    }

    @Override
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:mail)", User.class);
            query.setParameter("mail", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void deleteById(int id){
        User user = entityManager.find(User.class, id);
        if (user != null){
            entityManager.remove(user);
        }
    }

}
