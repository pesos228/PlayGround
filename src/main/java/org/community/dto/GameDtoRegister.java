package org.community.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GameDtoRegister {
    private String name;
    private String description;
    private Set<GenreDtoName> genres;
}
