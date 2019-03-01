package com.esgi.project.captchup.imageprocessing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.esgi.project.captchup.R;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.net.Uri;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class VisionAPIProcess extends AsyncTask<String, String, String> {

    Uri imageURI;
    Context context;
    public static final String VISION_API_KEY = "AIzaSyDgZc15rtLGH-UPZ7w3LQPJlL1zd5KyBtU";
    ImageProcessingFragment activity;


    public VisionAPIProcess(Uri imageURI, Context context, ImageProcessingFragment activity) {
        this.imageURI = imageURI;
        this.context = context;
        this.activity = activity;
    }

    /**
     * Converts Uri to base 64 encoded image
     */
    private Image getImageEncodeImage(Uri image) throws FileNotFoundException {

        final InputStream imageStream = context.getContentResolver().openInputStream(image);
        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

        Image base64EncodedImage = new Image();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        base64EncodedImage.encodeContent(imageBytes);
        return base64EncodedImage;
    }


    @Override
    protected String doInBackground(String... strings) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        Feature feature = new Feature();
        feature.setType("LABEL_DETECTION");
        feature.setMaxResults(10);

        List<Feature> featureList = new ArrayList<>();
        featureList.add(feature);

        List<AnnotateImageRequest> annotateImageRequests = new ArrayList<>();
        AnnotateImageRequest annotateImageReq = new AnnotateImageRequest();
        annotateImageReq.setFeatures(featureList);
        try {
            annotateImageReq.setImage(getImageEncodeImage(imageURI));
        } catch (FileNotFoundException e) {
            Log.e(context.getString(R.string.error), e.getMessage());
        }
        annotateImageRequests.add(annotateImageReq);

        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer = new VisionRequestInitializer(VISION_API_KEY);

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(annotateImageRequests);

        Vision.Images.Annotate annotateRequest = null;
        try {
            annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
        } catch (IOException e) {
            Log.e(context.getString(R.string.error), e.getMessage());
        }
        annotateRequest.setDisableGZipContent(true);
        BatchAnnotateImagesResponse response = null;
        try {
            response = annotateRequest.execute();
        } catch (IOException e) {
            Log.e(context.getString(R.string.error), e.getMessage());
        }

        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.translatePredictions(s);
    }
}
