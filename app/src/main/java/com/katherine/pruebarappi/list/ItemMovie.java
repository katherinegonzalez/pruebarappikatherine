package com.katherine.pruebarappi.list;

import android.text.Layout;

/**
 * Created by katherinegonzalez on 25/04/18.
 */

public class ItemMovie {

    private String title;
    private String releaseDate;
    private String language;
    private String score;
    private Layout lytItem;

    public ItemMovie() {
    }

    public ItemMovie(String title, String releaseDate, String language, String score, Layout lytItem) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.language = language;
        this.score = score;
        this.lytItem = lytItem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Layout getLytItem() {
        return lytItem;
    }

    public void setLytItem(Layout lytItem) {
        this.lytItem = lytItem;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
