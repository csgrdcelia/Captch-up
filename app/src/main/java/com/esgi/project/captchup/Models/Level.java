package com.esgi.project.captchup.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable{
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
        if(predictions.size() < 3)
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
        if(predictions != null) {
            for (Prediction prediction : predictions) {
                if (prediction.value.equalsIgnoreCase(answer)) {
                    return prediction;
                }
            }
        }
        return null;
    }

    /**
     * Returns the prediction according to given number ( 0, 1, 2 )
     */
    public Prediction getPrediction(int predictionNumber) {
        if(predictions != null && predictionNumber >= 0 && predictionNumber < 3)
            return predictions.get(predictionNumber);

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
     * Returns the number of predictions of the current level
     */
    public int getNumberOfPredictions()
    {
        if (predictions == null)
            return 0;
        else
            return predictions.size();
    }

    /**
     * Resets the prediction list
     */
    public void resetPredictions() {
        predictions = null;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
