package com.esgi.project.captchup.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    public static final String LEVELS_ROOT = "levels";
    private String id;
    private String image;

    private List<Prediction> predictions;

    public Level() {
    }

    public Level(String id, String image)
    {
        this.id = id;
        this.image = image;
    }

    /**
     * Adds a prediction to te prediction list
     */
    public void addPrediction(Prediction prediction)
    {
        if(predictions == null)
            predictions = new ArrayList<>();

        predictions.add(prediction);
    }

    /**
     * Return number of prediction found of the total number
     */
    public String getAdvancement()
    {
        int completed = 0;

        if(predictions == null)
            predictions = new ArrayList<>();

        for (Prediction prediction : predictions)
        {
            if(prediction.found)
                completed += 1;
        }
        return completed + "/" + predictions.size();
    }

    /**
     * Checks if all predictions are found
     */
    public boolean isFinished() {

        if(predictions == null)
            predictions = new ArrayList<>();

        for(Prediction prediction : predictions) {
            if(!prediction.getFound()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a prediction matches the given answer
     */
    public boolean answerExists(String answer)
    {
        for(Prediction prediction : predictions) {
            if(prediction.value.equalsIgnoreCase(answer)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if given answer has already been found
     */
    public boolean answerHasAlreadyBeenFound(String answer) {
        for(Prediction prediction : predictions) {
            if(prediction.value.equalsIgnoreCase(answer)) {
                if(prediction.found == true)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns prediction related to given answer
     */
    public Prediction getPrediction(String answer)
    {
        for(Prediction prediction : predictions) {
            if(prediction.value.equalsIgnoreCase(answer)) {
                return prediction;
            }
        }
        return null;
    }

    /**
     * Returns the prediction according to given number ( 0, 1, 2 )
     */
    public Prediction getPrediction(int predictionNumber) {
        if(predictionNumber >= 1 && predictionNumber <= 3)
            return predictions.get(predictionNumber);
        else
            return null;
    }

    /**
     * Get the prediction number of the predition that matches the given answer.
     * Returns differents codes if answer is wrong/already found
     */
    public int getPredictionNumber(String answer)
    {
        int predictionNumber = 0;
        for(Prediction prediction : predictions) {
            if(prediction.value.equalsIgnoreCase(answer)) {
                return predictionNumber;
            }
            predictionNumber++;
        }
        return Prediction.WRONG_ANSWER;
    }

    /**
     * For test purposes
     */
    public static Prediction[] getPredictionList()
    {
        Prediction prediction1 = new Prediction("20", "Robot", 80.0, false);
        Prediction prediction2 = new Prediction("13", "Jeu", 85.0, false);
        Prediction prediction3 = new Prediction("15", "Test", 90.0, false);

        Prediction predictionList[] = new Prediction[3];
        predictionList[0] = prediction1;
        predictionList[1] = prediction2;
        predictionList[2] = prediction3;

        return predictionList;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }
}
