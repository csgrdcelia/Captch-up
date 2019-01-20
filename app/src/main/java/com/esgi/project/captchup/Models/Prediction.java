package com.esgi.project.captchup.Models;

public class Prediction {
    public static final String PREDICTIONS_ROOT = "predictions";
    public static final int WRONG_ANSWER = 3;
    public static final int ALREADY_FOUND = 4;

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

    public String getValue() {
        return value;
    }

    public Double getPrecision() {
        return precision;
    }

    public Boolean getFound() {
        return found;
    }
}
