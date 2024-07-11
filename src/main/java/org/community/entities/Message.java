package org.community.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message extends BaseEntity {
    private User sender;
    private User receiver;
    private String text;
    private LocalDateTime messageTime;

    protected Message() {
    }

    public Message( User sender, User receiver, String text, LocalDateTime messageTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.messageTime = messageTime;
    }


    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Column(name = "text", nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "message_time", nullable = false)
    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }
}
