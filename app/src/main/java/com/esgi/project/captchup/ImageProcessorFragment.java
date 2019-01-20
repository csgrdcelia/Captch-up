package com.esgi.project.captchup;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.esgi.project.captchup.Models.Image;
import com.esgi.project.captchup.Models.Level;
import com.esgi.project.captchup.Models.Prediction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageProcessorFragment extends Fragment {

    private static int RESULT_LOAD_IMG = 1;
    Uri imageURI;

    private ImageView ivSelectedImage;

    private StorageReference storageReference;
    private DatabaseReference databaseLevels;

    public ImageProcessorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_processor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference(Image.IMAGES_ROOT);
        databaseLevels = FirebaseDatabase.getInstance().getReference(Level.LEVELS_ROOT);
        ivSelectedImage = getView().findViewById(R.id.ivSelectedImage);
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
                Picasso.get().load(imageURI).into(ivSelectedImage);

                createLevel();

            } else {
                Toast.makeText(getContext(), getString(R.string.no_image_selected),
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.an_error_occured), Toast.LENGTH_LONG)
                    .show();
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

            String levelId = databaseLevels.push().getKey();
            Level level = new Level(levelId, Level.getPredictionList(), taskSnapshot.getStorage().getDownloadUrl().toString());
            databaseLevels.child(levelId).setValue(level);

            //Image image = new Image(fileName, taskSnapshot.getStorage().getDownloadUrl().toString());
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), getString(R.string.an_error_occured), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }


}
