package com.esgi.project.captchup.imageprocessing;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CacheImage {

    String urlFromImage;
    Context context;
    File fileCache;
    ImageView iv;

    public CacheImage(String url) {
        urlFromImage = url;
    }

    public CacheImage(ImageView iv, String url, Context context) {
        this.iv = iv;
        this.urlFromImage = url;
        this.context = context;
    }

    public void run() {
        fileCache = new File(context.getCacheDir() + "/" + getFileName());
        if (!fileCache.exists()) {
            new CacheSaver(this).execute();
        } else {
            Picasso.get().load(fileCache).centerCrop().fit().into(iv);
        }
    }

    /**
     * Parses the file name from the url of stored image
     */
    public String getFileName() {
        int start = "https://firebasestorage.googleapis.com/v0/b/captchup-22a63.appspot.com/o/images%".length();
        int end = urlFromImage.indexOf('?');
        return urlFromImage.substring(start, end);
    }

    /**
     * Save the given image in bitmap to the cache storage
     */
    protected void saveImageToInternalStorage(Bitmap bitmap) {

        File file = new File(context.getCacheDir(), getFileName());

        try {
            OutputStream stream = null;

            stream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            stream.flush();

            stream.close();

        } catch (IOException e)
        {
            Log.e("error", e.getMessage());
        }
    }

}




