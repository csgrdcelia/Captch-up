package com.esgi.project.captchup;

import android.media.Image;

import java.util.List;

public class Level {
    private int id;
    private List<Prediction> predictions;
    private String imageUrl;

    public Level() {
    }

    public Level(int id, List<Prediction> predictions, String image) {
        this.id = id;
        this.predictions = predictions;
        this.imageUrl = image;
    }

    public String getAdvancement()
    {
        int completed = 0;
        for (Prediction prediction : predictions)
        {
            if(prediction.found)
                completed += 1;
        }
        return completed + "/3";
    }

    public String getImage() {
        return imageUrl;
    }
}
