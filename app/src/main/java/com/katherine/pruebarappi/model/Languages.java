package com.katherine.pruebarappi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public class Languages {

    @SerializedName("iso_639_1")
    private String iso6391;
    @SerializedName("name")
    private String name;


    public Languages() {
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
