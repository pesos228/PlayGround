package org.community.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "client")
public class User {

    private int id;
    private String email;
    private String password;
    private LocalDateTime reg_time;
    private List<User> list_friends = new ArrayList<>();
    private List<Game> list_games = new ArrayList<>();
    private List<Message> sent_messages = new ArrayList<>();
    private List<Message> received_messages = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Discussion> discussions = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();

    public User() {
    }

    public User(String email, String password, LocalDateTime reg_time, List<User> list_friends, List<Game> list_games, List<Message> sent_messages, List<Message> received_messages, List<Comment> comments, List<Discussion> discussions, List<Feedback> feedbacks) {
        this.email = email;
        this.password = password;
        this.reg_time = reg_time;
        this.list_friends = list_friends;
        this.list_games = list_games;
        this.sent_messages = sent_messages;
        this.received_messages = received_messages;
        this.comments = comments;
        this.discussions = discussions;
        this.feedbacks = feedbacks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public LocalDateTime getReg_time() {
        return reg_time;
    }

    public void setReg_time(LocalDateTime reg_time) {
        this.reg_time = reg_time;
    }
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    public List<User> getList_friends() {
        return list_friends;
    }

    public void setList_friends(List<User> list_friends) {
        this.list_friends = list_friends;
    }

    @ManyToMany
    @JoinTable(
            name = "user_games",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    public List<Game> getList_games() {
        return list_games;
    }

    public void setList_games(List<Game> list_games) {
        this.list_games = list_games;
    }

    @OneToMany(mappedBy = "sender")
    public List<Message> getSent_messages() {
        return sent_messages;
    }

    public void setSent_messages(List<Message> sent_messages) {
        this.sent_messages = sent_messages;
    }

    @OneToMany(mappedBy = "receiver")
    public List<Message> getReceived_messages() {
        return received_messages;
    }

    public void setReceived_messages(List<Message> received_messages) {
        this.received_messages = received_messages;
    }

    @OneToMany(mappedBy = "author_id")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "creator_id")
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    @OneToMany(mappedBy = "user_id")
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
