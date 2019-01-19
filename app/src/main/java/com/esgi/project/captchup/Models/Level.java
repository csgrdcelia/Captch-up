package com.esgi.project.captchup.Models;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private int id;
    private Prediction[] predictions;
    private String imageUrl;

    static ArrayList<Level> testLevels = getTestsLevels();

    private static ArrayList<Level> getTestsLevels() {

        Prediction prediction1 = new Prediction(20, "Robot", 80.0, true);
        Prediction prediction2 = new Prediction(13, "Jeu", 85.0, true);
        Prediction prediction3 = new Prediction(15, "Test", 90.0, true);
        Prediction prediction4 = new Prediction(15, "Doodle", 90.0, false);

        Prediction predictionList1[] = new Prediction[3];
        predictionList1[0] = prediction1;
        predictionList1[1] = prediction2;
        predictionList1[2] = prediction3;

        Prediction predictionList2[] = new Prediction[3];
        predictionList2[0] = prediction1;
        predictionList2[1] = prediction2;
        predictionList2[2] = prediction4;

        Level level1 = new Level(1, predictionList1, "url");
        Level level2 = new Level(2, predictionList2, "url2");

        ArrayList<Level> levels = new ArrayList<>();
        levels.add(level1);
        levels.add(level2);

        return levels;
    }

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
                if(predictions[i].found) {
                    return Prediction.ALREADY_FOUND;
                }
                else {
                    predictions[i].found = true;
                    return i;
                }
            }
        }
        return Prediction.WRONG_ANSWER;
    }

    public static ArrayList<Level> getFinishedLevels() {
        //TODO: replace with database query
        ArrayList<Level> levels = new ArrayList<>();

        for (Level level : testLevels) {
            if(level.isFinished())
            {
                levels.add(level);
            }
        }

        return levels;
    }

    public static List<Level> getUnfinishedLevels() {
        //TODO: replace with database query
        ArrayList<Level> levels = new ArrayList<>();

        for (Level level : testLevels) {
            if(!level.isFinished())
            {
                levels.add(level);
            }
        }

        return levels;
    }

    public static Level getLevel(int levelId) {
        //TODO: replace with database query

        for (Level level : testLevels) {
            if(level.getId() == levelId)
                return level;
        }
        return null;
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

    /**
     * Checks if all predictions are found
     */
    public boolean isFinished() {
        for(Prediction prediction : predictions) {
            if(!prediction.getFound()) {
                return false;
            }
        }
        return true;
    }

    public String getImage() {
        return imageUrl;
    }

    public int getId() {
        return id;
    }
}
