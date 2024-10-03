package org.community.dto;

public class CommentDtoList {
    private String authorEmail;
    private String text;

    protected CommentDtoList() {
    }

    public CommentDtoList(String authorEmail, String text) {
        this.authorEmail = authorEmail;
        this.text = text;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
