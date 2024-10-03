package org.community.dto;

public class FeedBackDtoMy {
    private int id;
    private String gameName;
    private String text;
    private float rating;

    protected FeedBackDtoMy() {
    }

    public FeedBackDtoMy(int id, String gameName, String text, float rating) {
        this.id = id;
        this.gameName = gameName;
        this.text = text;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
