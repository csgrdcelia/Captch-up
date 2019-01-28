package com.esgi.project.captchup.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CacheImage {

    public String urlFromImage;
    Context context;
    Bitmap bitmap;
    File fileCache;
    ImageView iv;

    public CacheImage(ImageView iv, String url, Context context) {
        this.iv = iv;
        this.urlFromImage = url;
        this.context = context;
    }

    public void run() {
        fileCache = new File(context.getCacheDir() + "/" + getFileName());
        if (!fileCache.exists()) {
            new CacheSaver().execute();
        } else {
            Picasso.get().load(fileCache).centerCrop().fit().into(iv);
        }
    }


    public String getFileName() {
        int start = "https://firebasestorage.googleapis.com/v0/b/captchup-22a63.appspot.com/o/images%".length();
        int end = urlFromImage.indexOf('?');
        return urlFromImage.substring(start, end);
    }


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
            e.printStackTrace();
        }
    }

    class CacheSaver extends AsyncTask<Void, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) new URL(urlFromImage).openConnection();

                connection.connect();

                InputStream inputStream = connection.getInputStream();

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                return bmp;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (result != null) {
                saveImageToInternalStorage(result);
                Picasso.get().load(fileCache).centerCrop().fit().into(iv);
            }
        }
    }
}




