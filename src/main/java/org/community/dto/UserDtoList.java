package org.community.dto;

public class UserDtoList {
    private int id;
    private String email;
    private int friendsCount;

    protected UserDtoList() {
    }

    public UserDtoList(int id, String email, int friendsCount) {
        this.id = id;
        this.email = email;
        this.friendsCount = friendsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }
}
