package com.esgi.project.captchup.ImageProcessing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class CacheSaver extends AsyncTask<Void, String, Bitmap> {

    private CacheImage cacheImage;

    public CacheSaver(CacheImage cacheImage) {
        this.cacheImage = cacheImage;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) new URL(cacheImage.urlFromImage).openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            return BitmapFactory.decodeStream(bufferedInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (result != null) {
            cacheImage.saveImageToInternalStorage(result);
            Picasso.get().load(cacheImage.fileCache).centerCrop().fit().into(cacheImage.iv);
        }
    }
}
