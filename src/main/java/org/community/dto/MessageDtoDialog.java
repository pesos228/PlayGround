package org.community.dto;

public class MessageDtoDialog {
    private String senderEmail;
    private String receiverEmail;
    private String text;

    protected MessageDtoDialog() {
    }

    public MessageDtoDialog(String senderEmail, String receiverEmail, String text) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.text = text;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
