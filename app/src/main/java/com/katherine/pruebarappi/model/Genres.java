package com.katherine.pruebarappi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public class Genres {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;

    public Genres(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genres() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
