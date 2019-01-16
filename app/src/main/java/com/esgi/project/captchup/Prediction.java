package com.esgi.project.captchup;

public class Prediction {
    int id;
    String value;
    Double precision;
    Boolean found;

    public Prediction() {
    }


    public Prediction(int id, String value, Double precision, Boolean found) {
        this.id = id;
        this.value = value;
        this.precision = precision;
        this.found = found;
    }
}
