package org.community.repository.impl;

import jakarta.persistence.TypedQuery;
import org.community.entities.Message;
import org.community.repository.AbstractBaseRepository;
import org.community.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepositoryImpl extends AbstractBaseRepository<Message, Integer> implements MessageRepository {

    public MessageRepositoryImpl() {
        super(Message.class);
    }
    @Override
    public List<Message> findDialogBetweenUsers(int userId1, int userId2) {
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1) ORDER BY m.messageTime", Message.class);
        query.setParameter("userId1", userId1);
        query.setParameter("userId2", userId2);
        return query.getResultList();
    }
}
