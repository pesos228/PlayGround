package org.community.dto;

public class FeedBackDto {
    private String userEmail;
    private String gameName;
    private String text;
    private float rating;

    protected FeedBackDto() {
    }

    public FeedBackDto(String userEmail, String gameName, String text, float rating) {
        this.userEmail = userEmail;
        this.gameName = gameName;
        this.text = text;
        this.rating = rating;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
