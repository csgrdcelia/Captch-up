package com.esgi.project.captchup;

import com.esgi.project.captchup.models.Prediction;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class PredictionTest {

    @Test
    public void getFirst3FromJsonTest(){
        String json = "{\"responses\":" +
                "[{\"labelAnnotations\":[{\"description\":\"Vegetation\",\"mid\":\"/m/01fnns\",\"score\":0.9326158165931702,\"topicality\":0.9326158165931702}," +
                "{\"description\":\"Tree\",\"mid\":\"/m/07j7r\",\"score\":0.9045121073722839,\"topicality\":0.9045121073722839}," +
                "{\"description\":\"Plant\",\"mid\":\"/m/05s2s\",\"score\":0.8957617282867432,\"topicality\":0.8957617282867432}," +
                "{\"description\":\"Botany\",\"mid\":\"/m/01bwr\",\"score\":0.8669448494911194,\"topicality\":0.8669448494911194}," +
                "{\"description\":\"Palm tree\",\"mid\":\"/m/0cdl1\",\"score\":0.8556838035583496,\"topicality\":0.8556838035583496}," +
                "{\"description\":\"Leaf\",\"mid\":\"/m/09t49\",\"score\":0.8342376351356506,\"topicality\":0.8342376351356506}," +
                "{\"description\":\"Arecales\",\"mid\":\"/m/0lhr\",\"score\":0.8242786526679993,\"topicality\":0.8242786526679993}," +
                "{\"description\":\"Biome\",\"mid\":\"/m/01jb4\",\"score\":0.7930392622947693,\"topicality\":0.7930392622947693}," +
                "{\"description\":\"Flower\",\"mid\":\"/m/0c9ph5\",\"score\":0.791316568851471,\"topicality\":0.791316568851471}," +
                "{\"description\":\"Greenhouse\",\"mid\":\"/m/0lyf_\",\"score\":0.7791448831558228,\"topicality\":0.7791448831558228}]}]}";

        List<Prediction> predictionsFound = Prediction.getFirst3Predictions(json);

        Assert.assertTrue(predictionsFound.size() == 3);

        Assert.assertEquals(predictionsFound.get(0).getValue(), "Vegetation");
        Assert.assertEquals(predictionsFound.get(0).getPrecision(), Double.valueOf(0.9326158165931702));
        Assert.assertFalse(predictionsFound.get(0).getFound());

        Assert.assertEquals(predictionsFound.get(1).getValue(), "Tree");
        Assert.assertEquals(predictionsFound.get(1).getPrecision(), Double.valueOf(0.9045121073722839));
        Assert.assertFalse(predictionsFound.get(1).getFound());

        Assert.assertEquals(predictionsFound.get(2).getValue(), "Plant");
        Assert.assertEquals(predictionsFound.get(2).getPrecision(), Double.valueOf(0.8957617282867432));
        Assert.assertFalse(predictionsFound.get(2).getFound());
    }
}
