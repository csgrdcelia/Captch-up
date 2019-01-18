package com.esgi.project.captchup.Models;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int id;
    private Prediction[] predictions;
    private String imageUrl;

    public Level() {
    }

    public Level(int id, Prediction[] predictions, String image) {
        this.id = id;
        this.predictions = predictions;
        this.imageUrl = image;
    }

    public Prediction getPrediction(int predictionNumber)
    {
        if(predictionNumber >= 1 && predictionNumber <= 3)
            return predictions[predictionNumber - 1];
        else
            return null;
    }

    public int getPredictionNumber(String answer)
    {
        for (int i = 0; i < 3; i++)
        {
            if(predictions[i].value.equalsIgnoreCase(answer))
            {
                if(predictions[i].found == false) {
                    predictions[i].found = true;
                    return i;
                }
            }
        }
        return 3;
    }

    public static ArrayList<Level> getFinishedLevels() {
        //TODO: replace with database query
        Prediction prediction1 = new Prediction(20, "Robot", 80.0, true);
        Prediction prediction2 = new Prediction(13, "Jeu", 85.0, true);
        Prediction prediction3 = new Prediction(15, "Test", 90.0, true);

        Prediction predictions[] = new Prediction[3];
        predictions[0] = prediction1;
        predictions[1] = prediction2;
        predictions[2] = prediction3;

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

        Prediction predictions[] = new Prediction[3];
        predictions[0] = prediction1;
        predictions[1] = prediction2;
        predictions[2] = prediction3;

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

        Prediction predictions[] = new Prediction[3];
        predictions[0] = prediction1;
        predictions[1] = prediction2;
        predictions[2] = prediction3;

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
