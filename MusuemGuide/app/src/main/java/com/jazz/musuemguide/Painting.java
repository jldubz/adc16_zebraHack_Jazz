package com.jazz.musuemguide;

/**
 * Created by Jon-Luke.West on 8/2/2016.
 */
public class Painting {

    String title;
    String description;
    String assetPath;
    String author;

    public Painting(String title, String description, String assetPath, String author) {
        this.title = title;
        this.description = description;
        this.assetPath = assetPath;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssetPath() {
        return assetPath;
    }

    public void setAssetPath(String assetPath) {
        this.assetPath = assetPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
