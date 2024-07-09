package org.community.models;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Genre {
    private int id;
    private int name;

    public Genre() {
    }

    public Genre(int name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, unique = true)
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
