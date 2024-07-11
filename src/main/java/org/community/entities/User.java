package org.community.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class User extends BaseEntity {

    private String email;
    private String password;
    private LocalDateTime regTime;
    private List<User> listFriends = new ArrayList<>();
    private List<Game> listGames = new ArrayList<>();
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Discussion> discussions = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();

    protected User() {
    }

    public User(String email, String password, LocalDateTime regTime, List<User> listFriends, List<Game> listGames, List<Message> sentMessages, List<Message> receivedMessages, List<Comment> comments, List<Discussion> discussions, List<Feedback> feedbacks) {
        this.email = email;
        this.password = password;
        this.regTime = regTime;
        this.listFriends = listFriends;
        this.listGames = listGames;
        this.sentMessages = sentMessages;
        this.receivedMessages = receivedMessages;
        this.comments = comments;
        this.discussions = discussions;
        this.feedbacks = feedbacks;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "reg_time", nullable = false)
    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(LocalDateTime regTime) {
        this.regTime = regTime;
    }
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    public List<User> getListFriends() {
        return listFriends;
    }

    public void setListFriends(List<User> listFriends) {
        this.listFriends = listFriends;
    }

    @ManyToMany
    @JoinTable(
            name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    public List<Game> getListGames() {
        return listGames;
    }

    public void setListGames(List<Game> listGames) {
        this.listGames = listGames;
    }

    @OneToMany(mappedBy = "sender")
    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    @OneToMany(mappedBy = "receiver")
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @OneToMany(mappedBy = "author")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "creator")
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    @OneToMany(mappedBy = "user")
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
