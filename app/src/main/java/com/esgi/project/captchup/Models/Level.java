package com.esgi.project.captchup.Models;

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

    public static ArrayList<Level> getFinishedLevels() {
        //TODO: replace with database query
        Prediction prediction1 = new Prediction(20, "Robot", 80.0, true);
        Prediction prediction2 = new Prediction(13, "Jeu", 85.0, true);
        Prediction prediction3 = new Prediction(15, "Test", 90.0, true);

        List<Prediction> predictions = new ArrayList<>();
        predictions.add(prediction1);
        predictions.add(prediction2);
        predictions.add(prediction3);

        Level level = new Level(50, predictions, "url");
        Level level2 = new Level(51, predictions, "url2");
        ArrayList<Level> levels = new ArrayList<>();
        levels.add(level);
        levels.add(level2);

        return levels;
    }

    public static List<Level> getUnfinishedLevels() {
        //TODO: replace with database query
        Prediction prediction1 = new Prediction(50, "Robot", 80.0, false);
        Prediction prediction2 = new Prediction(23, "Jeu", 85.0, true);
        Prediction prediction3 = new Prediction(32, "Test", 90.0, false);

        List<Prediction> predictions = new ArrayList<>();
        predictions.add(prediction1);
        predictions.add(prediction2);
        predictions.add(prediction3);

        Level level = new Level(30, predictions, "url");
        ArrayList<Level> levels = new ArrayList<>();
        levels.add(level);

        return levels;
    }

    public static Level getLevel(int levelId) {
        //TODO: replace with database query
        Prediction prediction1 = new Prediction(50, "Robot", 80.0, false);
        Prediction prediction2 = new Prediction(23, "Jeu", 85.0, true);
        Prediction prediction3 = new Prediction(32, "Test", 90.0, false);

        List<Prediction> predictions = new ArrayList<>();
        predictions.add(prediction1);
        predictions.add(prediction2);
        predictions.add(prediction3);

        return new Level(30, predictions, "url");
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

    public int getId() {
        return id;
    }
}
