package org.community.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoList {
    private int id;
    private String email;
    private int friendsCount;
}
