package com.esgi.project.captchup.ImageProcessing;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esgi.project.captchup.Game.GameFragment;
import com.esgi.project.captchup.MainActivity;
import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.Models.Prediction;
import com.esgi.project.captchup.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageProcessingFragment extends Fragment {

    public static final String IMAGES_ROOT = "images";
    public static final String VISION_API_KEY = "AIzaSyDgZc15rtLGH-UPZ7w3LQPJlL1zd5KyBtU";
    private static int RESULT_LOAD_IMG = 1;
    Uri imageURI;
    Level createdLevel;

    private ImageView ivSelectedImage;
    private TextView tvResult;
    private ProgressBar pbProcessing;
    private ImageButton ibPlay;

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
        setActionBarTitle();

        storageReference = FirebaseStorage.getInstance().getReference(IMAGES_ROOT);
        databaseLevels = FirebaseDatabase.getInstance().getReference(Level.LEVELS_ROOT + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        ivSelectedImage = getView().findViewById(R.id.ivSelectedImage);
        tvResult = getView().findViewById(R.id.textViewResult);
        pbProcessing = getView().findViewById(R.id.pbProcessing);
        ibPlay = getView().findViewById(R.id.ibPlay);
        increaseHitAreaOfPlayButton();
        ibPlay.setOnClickListener(v -> {
            if (createdLevel != null) {
                MainActivity activity = (MainActivity) getView().getContext();
                Fragment myFragment = GameFragment.newInstance(createdLevel);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, myFragment).addToBackStack(null).commit();
            }
        });

        loadImagefromGallery();
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

            } else {
                Toast.makeText(getContext(), getString(R.string.no_image_selected),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.an_error_occured), Toast.LENGTH_LONG)
                    .show();
        }

    }

    /**
     * Runs the gallery picker
     */
    public void loadImagefromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    /**
     * Calls the Vision API (AsyncTask)
     */
    public void callVisionAPI() {

        VisionAPIProcess process = new VisionAPIProcess(imageURI, getContext(), this);
        process.execute();
        tvResult.setText(getString(R.string.google_analyze));
        tvResult.setVisibility(View.VISIBLE);
        pbProcessing.setVisibility(View.VISIBLE);

    }

    /**
     * Translates the list of predictions en -> fr
     * This is called from vision api task
     */
    public void translatePredictions(String apiResult) {
        tvResult.setText(getString(R.string.level_creation));
        List<Prediction> predictions = Prediction.getFirst3Predictions(apiResult);
        new PredictionTranslator(this).execute(predictions);
    }

    /**
     * Creates a level according to given list of translated predictions
     * This is called from prediction translator task
     */
    public void createLevel(List<Prediction> predictions) {

        if (predictions != null) {

            String fileName = System.currentTimeMillis() + "." + getFileExtension(imageURI);

            StorageReference fileReference = storageReference.child(fileName);

            fileReference.putFile(imageURI).addOnSuccessListener(taskSnapshot -> {

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful()) ;
                Uri downloadUrl = urlTask.getResult();

                String levelId = databaseLevels.push().getKey();
                createdLevel = new Level(levelId, String.valueOf(downloadUrl));
                databaseLevels.child(levelId).setValue(createdLevel);

                DatabaseReference databasePredictions = FirebaseDatabase.getInstance().getReference(Level.LEVELS_ROOT + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + levelId + "/" + Prediction.PREDICTIONS_ROOT);
                for (Prediction prediction : predictions) {
                    String predictionId = databasePredictions.push().getKey();
                    Prediction p = new Prediction(predictionId, prediction.getValue(), prediction.getPrecision());
                    createdLevel.addPrediction(p);
                    databasePredictions.child(predictionId).setValue(p);
                }

            }).addOnFailureListener(e -> Toast.makeText(getContext(), getString(R.string.an_error_occured), Toast.LENGTH_LONG)
                    .show());

            pbProcessing.setVisibility(View.GONE);
            ibPlay.setVisibility(View.VISIBLE);
            tvResult.setText(getString(R.string.level_ready));
        } else {
            pbProcessing.setVisibility(View.GONE);
            tvResult.setText(getString(R.string.google_needs_training));
        }
    }

    /**
     * Sets the action bar title
     */
    private void setActionBarTitle() {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
            actionBar.setTitle(R.string.import_image);
    }

    /**
     * Returns the file extension of the given uri
     */
    public String getFileExtension(Uri uri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    /**
     * This increase the hit area of the play button
     */
    private void increaseHitAreaOfPlayButton() {
        final View parent = (View) ibPlay.getParent();  // button: the view you want to enlarge hit area
        parent.post(() -> {
            final Rect rect = new Rect();
            ibPlay.getHitRect(rect);
            rect.top -= 100;
            rect.left -= 100;
            rect.bottom += 100;
            rect.right += 100;
            parent.setTouchDelegate( new TouchDelegate( rect , ibPlay));
        });
    }


}
