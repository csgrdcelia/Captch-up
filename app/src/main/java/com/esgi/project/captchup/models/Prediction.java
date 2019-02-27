package com.esgi.project.captchup.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public Prediction(String value, Double precision, Boolean found) {
        this.value = value;
        this.precision = precision;
        this.found = found;
    }

    /**
     * This gets the first 3 predictions of given json (which is returned by vision api)
     */
    public static List<Prediction> getFirst3Predictions(String json) {
        try {
            List<Prediction> predictions = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray responses = jsonObject.getJSONArray("responses");
            JSONObject value = responses.getJSONObject(0);
            JSONArray annotations = value.getJSONArray("labelAnnotations");

            for (int i = 0; i < annotations.length(); i++) {

                JSONObject c = annotations.getJSONObject(i);
                String description = c.getString("description");
                Double score = c.getDouble("score");

                if(score > 0.80 && !description.trim().contains(" ")) {
                    Prediction p = new Prediction(description, score, false);
                    predictions.add(p);

                    if(predictions.size() == 3)
                        return predictions;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<Prediction>();
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

    public void setValue(String value) {
        this.value = value;
    }
}
