package org.community.dto;

public class GameDtoName {
    private String name;

    protected GameDtoName() {
    }

    public GameDtoName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
