package com.esgi.project.captchup.imageprocessing;


import android.os.AsyncTask;

import com.esgi.project.captchup.models.Prediction;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PredictionTranslator extends AsyncTask<List<Prediction>, Prediction, List<Prediction>> {
    ImageProcessingFragment activity;

    public PredictionTranslator(ImageProcessingFragment activity) {
        this.activity = activity;
    }

    @Override
    protected List<Prediction> doInBackground(List<Prediction>... predictions) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if(predictions[0] != null) {
            for (Prediction p : predictions[0]) {
                try {
                    StringBuilder result = new StringBuilder();
                    String encodedText = URLEncoder.encode(p.getValue(), "UTF-8");
                    String urlStr = "https://www.googleapis.com/language/translate/v2?key=" + "AIzaSyDgZc15rtLGH-UPZ7w3LQPJlL1zd5KyBtU" + "&q=" + encodedText + "&target=fr&source=en";

                    URL url = new URL(urlStr);

                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    InputStream stream;
                    if (conn.getResponseCode() == 200) //success
                    {
                        stream = conn.getInputStream();
                    } else
                        stream = conn.getErrorStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    JsonParser parser = new JsonParser();

                    JsonElement element = parser.parse(result.toString());

                    if (element.isJsonObject()) {
                        JsonObject obj = element.getAsJsonObject();
                        if (obj.get("error") == null) {
                            String translatedText = obj.get("data").getAsJsonObject().
                                    get("translations").getAsJsonArray().
                                    get(0).getAsJsonObject().
                                    get("translatedText").getAsString();
                            p.setValue(translatedText);

                        }
                    }

                    if (conn.getResponseCode() != 200) {
                        System.err.println(result);
                    }

                } catch (IOException | JsonSyntaxException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
        return predictions[0];
    }

    @Override
    protected void onPostExecute(List<Prediction> predictions) {
        super.onPostExecute(predictions);
        activity.createLevel(predictions);
    }
}
