package org.community.controller;

import org.community.dto.MessageDtoDialog;
import org.community.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/{userId}/dialog/{friendId}")
    public List<MessageDtoDialog> seeDialog(@PathVariable int userId, @PathVariable int friendId){
        return messageService.seeDialog(userId,friendId);
    }
    @PostMapping("/{userId}/send/{friendId}/{text}")
    public void sendMessage(@PathVariable int userId, @PathVariable int friendId, @PathVariable String text){
        messageService.sendMessage(userId,friendId, text);
    }

}
