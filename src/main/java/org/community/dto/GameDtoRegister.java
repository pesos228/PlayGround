package org.community.dto;

import java.util.Set;

public class GameDtoRegister {
    private String name;
    private String description;
    private Set<GenreDtoName> genres;

    protected GameDtoRegister() {
    }

    public GameDtoRegister(String name, String description, Set<GenreDtoName> genres) {
        this.name = name;
        this.description = description;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GenreDtoName> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreDtoName> genres) {
        this.genres = genres;
    }
}
