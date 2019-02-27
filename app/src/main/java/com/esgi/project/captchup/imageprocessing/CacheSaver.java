package com.esgi.project.captchup.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

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
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) new URL(cacheImage.urlFromImage).openConnection();
            if(connection != null)
            {
                connection.connect();
                inputStream = connection.getInputStream();
            }
        } catch(Exception e)
        {
            Log.e("error", e.getMessage());
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            return BitmapFactory.decodeStream(bufferedInputStream);

        } catch (Exception e) {
            Log.e("error", e.getMessage());
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
