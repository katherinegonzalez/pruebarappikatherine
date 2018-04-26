package com.katherine.pruebarappi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public class BelongsToCollection {

    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String posterpath;
    @SerializedName("backdrop_path")
    private String backdropPath;

    public BelongsToCollection() {
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

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}
