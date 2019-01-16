package com.esgi.project.captchup;

import android.media.Image;

import java.util.ArrayList;
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

    static ArrayList<Level> getFinishedLevels() {
        Prediction prediction1 = new Prediction(1, "Robot", 80.0, true);
        Prediction prediction2 = new Prediction(2, "Jeu", 85.0, true);
        Prediction prediction3 = new Prediction(3, "Test", 90.0, true);

        List<Prediction> predictions = new ArrayList<>();
        predictions.add(prediction1);
        predictions.add(prediction2);
        predictions.add(prediction3);

        Level level = new Level(1, predictions, "url");
        ArrayList<Level> levels = new ArrayList<>();
        levels.add(level);
        levels.add(level);

        return levels;
    }

    public static List<Level> getUnfinishedLevels() {
        Prediction prediction1 = new Prediction(1, "Robot", 80.0, false);
        Prediction prediction2 = new Prediction(2, "Jeu", 85.0, true);
        Prediction prediction3 = new Prediction(3, "Test", 90.0, false);

        List<Prediction> predictions = new ArrayList<>();
        predictions.add(prediction1);
        predictions.add(prediction2);
        predictions.add(prediction3);

        Level level = new Level(1, predictions, "url");
        ArrayList<Level> levels = new ArrayList<>();
        levels.add(level);

        return levels;
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
