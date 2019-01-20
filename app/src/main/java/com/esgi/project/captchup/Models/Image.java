package com.esgi.project.captchup.Models;

import android.content.ContentResolver;
import android.net.Uri;

public class Image {
    public static final String IMAGES_ROOT = "images";
    private String id;
    private String url;

    public Image() {
        // empty constructor needed
    }

    public Image(String id, String url) {
        this.id = id;
        this.url = url;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
