package org.community.dto;

public class CommentDtoAdd {
    private String text;
    private String authorEmail;
    private int discussionId;

    protected CommentDtoAdd() {
    }

    public CommentDtoAdd(String text, String authorEmail, int discussionId) {
        this.text = text;
        this.authorEmail = authorEmail;
        this.discussionId = discussionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public int getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }
}
