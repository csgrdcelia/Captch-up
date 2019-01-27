package com.esgi.project.captchup.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class CacheImage {

    String urlFromImage;
    String cacheDir;
    Context context;
    Bitmap bitmap;
    File fileCache;

    public CacheImage(String url, Context context)
    {
        this.urlFromImage = url;
        this.cacheDir = context.getCacheDir().getPath();
        this.context = context;
    }

    public String getPath() {
        fileCache = new File(cacheDir + "/" + getFileName());
        if(!fileCache.exists()){
            try {
                new BitmapGetter().execute().get();
                saveImageToCache();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return fileCache.getPath();
    }


    public String getFileName() {
        int start = "https://firebasestorage.googleapis.com/v0/b/captchup-22a63.appspot.com/o/images%".length();
        int end = urlFromImage.indexOf('?');
        return urlFromImage.substring(start, end);
    }

    public void saveImageToCache() {
        try {
            if (bitmap != null) {

                FileOutputStream out = null;

                out = new FileOutputStream(fileCache);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                out.flush();
                out.close();

                //File parent = filename.getParentFile();

                ContentValues image = getImageContent(fileCache);
        }
            //Uri result = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ContentValues getImageContent(File parent) {

        ContentValues image = new ContentValues();

        image.put(MediaStore.Images.Media.DISPLAY_NAME, getFileName());
        image.put(MediaStore.Images.Media.DESCRIPTION, "App Image");
        image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        image.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        image.put(MediaStore.Images.Media.ORIENTATION, 0);
        image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString()
                .toLowerCase().hashCode());
        image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.getName()
                .toLowerCase());
        image.put(MediaStore.Images.Media.SIZE, parent.length());
        image.put(MediaStore.Images.Media.DATA, parent.getAbsolutePath());

        return image;

    }

    public Bitmap getBitmapFromURL() {
        try {
            URL url = new URL(urlFromImage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            return null;
        }
    }

    class BitmapGetter extends AsyncTask<String, String, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();
            try {
                return Picasso.get().load(urlFromImage).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bitmap = result;
            //saveImageToCache();
        }
    }

}


