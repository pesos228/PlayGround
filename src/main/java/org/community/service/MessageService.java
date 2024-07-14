package org.community.service;

import org.community.entities.Message;
import org.community.entities.User;
import org.community.repository.impl.MessageRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepositoryImpl messageRepository;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepositoryImpl messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Transactional
    public void sendMessage(int senderId, int receiverId, String text){
        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);
        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("One or both users not found");
        }
        if (!userService.isFriends(senderId, receiverId)) {
            throw new RuntimeException("Users are not friends");
        }
        Message message = new Message(
                sender,
                receiver,
                text,
                LocalDateTime.now()
        );
        messageRepository.save(message);
    }

    public List<Message> seeDialog(int user1, int user2){
        List <Message> dialog = messageRepository.findDialogBetweenUsers(user1, user2);
        if (dialog.isEmpty()){
            throw new IllegalArgumentException("Dialog doesn't exists");
        }
        return dialog;
    }
}
