package com.esgi.project.captchup;


import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.Models.Prediction;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageProcessingFragment extends Fragment {

    public static final String IMAGES_ROOT = "images";
    public static final String VISION_API_KEY = "AIzaSyDgZc15rtLGH-UPZ7w3LQPJlL1zd5KyBtU";
    private static int RESULT_LOAD_IMG = 1;
    Uri imageURI;

    private ImageView ivSelectedImage;
    private TextView tvResult;

    private StorageReference storageReference;
    private DatabaseReference databaseLevels;

    public ImageProcessingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_processing, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference(IMAGES_ROOT);
        databaseLevels = FirebaseDatabase.getInstance().getReference(Level.LEVELS_ROOT);
        ivSelectedImage = getView().findViewById(R.id.ivSelectedImage);
        tvResult = getView().findViewById(R.id.textViewResult);
        loadImagefromGallery();
    }

    /**
     * Runs the gallery picker
     */
    public void loadImagefromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                imageURI = data.getData();
                Picasso.get().load(imageURI).centerCrop().fit().into(ivSelectedImage);

                callVisionAPI();

                //createLevel();
            } else {
                Toast.makeText(getContext(), getString(R.string.no_image_selected),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.an_error_occured), Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void callVisionAPI() {
        try {
            VisionAPIProcess process = new VisionAPIProcess(imageURI, getContext());
            String apiReturn = process.execute().get();
            tvResult.setText(apiReturn);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getFileExtension(Uri uri)
    {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void createLevel() {
        //TODO: call API
        // If image is validated
        String fileName = System.currentTimeMillis() + "." + getFileExtension(imageURI);

        StorageReference fileReference = storageReference.child(fileName);

        fileReference.putFile(imageURI).addOnSuccessListener(taskSnapshot -> {

            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
            while (!urlTask.isSuccessful());
            Uri downloadUrl = urlTask.getResult();

            String levelId = databaseLevels.push().getKey();
            Level level = new Level(levelId, String.valueOf(downloadUrl));
            databaseLevels.child(levelId).setValue(level);

            DatabaseReference databasePredictions = FirebaseDatabase.getInstance().getReference(Level.LEVELS_ROOT + "/" + levelId + "/" + Prediction.PREDICTIONS_ROOT);
            for (Prediction prediction : Level.getPredictionList()) {
                String predictionId = databasePredictions.push().getKey();
                Prediction p = new Prediction(predictionId, prediction.getValue(), prediction.getPrecision());
                databasePredictions.child(predictionId).setValue(p);
            }

        }).addOnFailureListener(e -> Toast.makeText(getContext(), getString(R.string.an_error_occured), Toast.LENGTH_LONG)
                .show());
    }


}
