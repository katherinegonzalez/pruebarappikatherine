package com.katherine.pruebarappi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by katherinegonzalez on 26/04/18.
 */

public class VideoResponse {

    @SerializedName("id")
    private Long id;
    @SerializedName("results")
    private List<Video> results;

    public VideoResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
