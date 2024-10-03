package org.community.dto;

public class UserDtoFriendsList {
    private String email;

    protected UserDtoFriendsList() {
    }

    public UserDtoFriendsList(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
