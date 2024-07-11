package org.community.repository;

import org.community.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT sm FROM User u JOIN u.sentMessages sm WHERE u.id = :userId")
    List<Message> findAllSentMessages(@Param(value = "userId") int userId);
    @Query("SELECT rm FROM User u JOIN u.receivedMessages rm WHERE u.id = :userId")
    List<Message> findAllReceivedMessages(@Param(value = "userId") int userId);
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR (m.sender.id = :userId2 AND m.receiver.id = :userId1) ORDER BY m.messageTime")
    List<Message> findDialogBetweenUsers(@Param(value = "userId1") int userId1, @Param(value = "userId2") int userId2);
}
