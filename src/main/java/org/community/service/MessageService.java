package org.community.service;

import org.community.dto.MessageDtoDialog;
import org.community.entities.Message;
import org.community.entities.User;
import org.community.exceptions.DialogNotFoundException;
import org.community.exceptions.UserNotFoundException;
import org.community.exceptions.UsersNotFriendsException;
import org.community.repository.MessageRepository;
import org.community.repository.impl.MessageRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService extends AbstractService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService, ModelMapper modelMapper) {
        super(modelMapper);
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Transactional
    public void sendMessage(int senderId, int receiverId, String text){
        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);
        if (sender == null || receiver == null) {
            throw new UserNotFoundException("One or both users not found");
        }
        if (!userService.isFriends(senderId, receiverId)) {
            throw new UsersNotFriendsException("Users are not friends");
        }
        Message message = new Message(
                sender,
                receiver,
                text,
                LocalDateTime.now()
        );
        messageRepository.save(message);
    }

    public List<MessageDtoDialog> seeDialog(int user1, int user2){
        List <Message> dialog = messageRepository.findDialogBetweenUsers(user1, user2);
        if (dialog.isEmpty()){
            throw new DialogNotFoundException(user2);
        }
        return dialog.stream()
                .map(message -> convertToDto(message,MessageDtoDialog.class))
                .collect(Collectors.toList());
    }
}
