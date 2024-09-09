package org.community.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.community.entities.Message;
import org.community.repository.MessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Message> findDialogBetweenUsers(int userId1, int userId2) {
        return entityManager.createQuery("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1) ORDER BY m.messageTime", Message.class)
            .setParameter("userId1", userId1)
            .setParameter("userId2", userId2)
            .getResultList();
    }

    @Override
    public void save(Message message) {
        if (entityManager.contains(message)) {
            entityManager.merge(message);
        } else {
            entityManager.persist(message);
        }
    }
}
