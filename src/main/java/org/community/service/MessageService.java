package org.community.service;

import org.community.dto.MessageDtoDialog;
import java.util.List;

public interface MessageService {
    void sendMessage(int senderId, int receiverId, String text);
    List<MessageDtoDialog> seeDialog(int user1, int user2);
}
