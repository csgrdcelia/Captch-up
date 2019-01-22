package com.esgi.project.captchup.Models;

import java.io.Serializable;

public class Prediction implements Serializable {
    public static final String PREDICTIONS_ROOT = "predictions";
    public static final int FIRST_PREDICTION = 0;
    public static final int SECOND_PREDICTION = 1;
    public static final int THIRD_PREDICTION = 2;
    public static final int WRONG_ANSWER = 3;

    String id;
    String value;
    Double precision;
    Boolean found;

    public Prediction() {
    }

    public Prediction(String id, String value, Double precision) {
        this.id = id;
        this.value = value;
        this.precision = precision;
        this.found = false;
    }

    public Prediction(String id, String value, Double precision, Boolean found) {
        this.id = id;
        this.value = value;
        this.precision = precision;
        this.found = found;
    }

    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public Double getPrecision() {
        return precision;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }
}
