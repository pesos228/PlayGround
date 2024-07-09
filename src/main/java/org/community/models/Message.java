package org.community.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Message {
    private int id;
    private User sender;
    private User receiver;
    private String text;
    private LocalDateTime message_time;

    public Message() {
    }

    public Message(int id, User sender, User receiver, String text, LocalDateTime message_time) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.message_time = message_time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "sender_id", nullable = false)
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @Column(name = "receiver_id", nullable = false)
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
    public LocalDateTime getMessage_time() {
        return message_time;
    }

    public void setMessage_time(LocalDateTime message_time) {
        this.message_time = message_time;
    }
}
