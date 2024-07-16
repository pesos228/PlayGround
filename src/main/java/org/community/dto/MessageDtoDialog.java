package org.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDtoDialog {
    private String senderEmail;
    private String receiverEmail;
    private String text;
}
