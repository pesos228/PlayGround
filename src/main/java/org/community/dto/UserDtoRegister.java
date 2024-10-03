package org.community.dto;

public class UserDtoRegister {
    private String email;
    private String password;

    protected UserDtoRegister() {
    }

    public UserDtoRegister(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
