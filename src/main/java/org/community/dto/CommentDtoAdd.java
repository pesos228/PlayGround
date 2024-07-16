package org.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDtoAdd {
    private String text;
    private String authorEmail;
    private int discussionId;
}
