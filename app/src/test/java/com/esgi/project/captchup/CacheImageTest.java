package com.esgi.project.captchup;

import android.widget.ImageView;

import com.esgi.project.captchup.ImageProcessing.CacheImage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CacheImageTest {

    CacheImage cacheImage;

    @Before
    public void setUp() throws Exception {
        cacheImage = new CacheImage("https://firebasestorage.googleapis.com/v0/b/captchup-22a63.appspot.com/o/images%2F1549272276522.jpg?alt=media&token=9011e670-5dd8-40a5-9a62-e169c3ed976c");
    }

    @Test
    public void getFileNameTest() {
        String result = cacheImage.getFileName();
        Assert.assertEquals(result, "2F1549272276522.jpg");
        Assert.assertNotEquals(result, "mauvais nom de fichier");
    }
}
