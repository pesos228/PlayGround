package org.community.repository;

import org.community.entities.Message;

import java.util.List;

public interface MessageRepository{
    List<Message> findDialogBetweenUsers(int userId1, int userId2);
    void save(Message message);
}
