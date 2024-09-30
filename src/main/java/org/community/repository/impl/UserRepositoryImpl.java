package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.community.entities.Game;
import org.community.entities.User;
import org.community.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findFriendsByUserId(int id) {
        return entityManager.createQuery("SELECT f FROM User u JOIN u.listFriends f WHERE u.id = :id AND u MEMBER OF f.listFriends", User.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public boolean existsFriendByUserIdAndFriendId(int userId, int friendId) {
        return entityManager.createQuery("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END FROM User u JOIN u.listFriends f WHERE u.id = :userId AND f.id = :friendId", Boolean.class)
                .setParameter("userId", userId)
                .setParameter("friendId", friendId)
                .getSingleResult();
    }

    @Override
    public List<Game> findGamesByUserId(int id) {
        return entityManager.createQuery("SELECT g FROM User u JOIN u.listGames g WHERE u.id = :id", Game.class)
            .setParameter("id", id)
            .getResultList();
    }

    @Override
    public boolean existsGameByUserIdAndGameId(int userId, int gameId) {
        return entityManager.createQuery("SELECT CASE WHEN COUNT(g) > 0 THEN TRUE ELSE FALSE END FROM User u JOIN u.listGames g WHERE u.id = :userId AND g.id = :gameId", Boolean.class)
            .setParameter("userId", userId)
            .setParameter("gameId", gameId)
            .getSingleResult();
    }

    @Override
    public User findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:mail)", User.class)
                .setParameter("mail", email)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(User user){
        if (entityManager.contains(user)) {
            entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }
    }

    @Override
    public User findById(int id){
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll(){
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

}
