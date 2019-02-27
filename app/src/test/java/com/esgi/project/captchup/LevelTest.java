package com.esgi.project.captchup;

import com.esgi.project.captchup.models.Level;
import com.esgi.project.captchup.models.Prediction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LevelTest {

    Level level;

    @Before
    public void setUp() throws Exception {
        level = new Level();
    }

    @Test
    public void addPredictionTest(){
        Prediction p = new Prediction();
        level.addPrediction(p);
        Assert.assertTrue(level.getNumberOfPredictions() == 1);

        level.addPrediction(p);
        level.addPrediction(p);
        Assert.assertTrue(level.getNumberOfPredictions() == 3);

        level.addPrediction(p);
        Assert.assertTrue(level.getNumberOfPredictions() == 3);

        level.resetPredictions();
    }

    @Test
    public void getAdvancementTest() {
        level.addPrediction(new Prediction("id","test",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, false));

        String result = level.getAdvancement();

        Assert.assertEquals(result, "2/3");

        level.resetPredictions();
    }

    @Test
    public void isFinishedTest() {
        boolean result;

        level.addPrediction(new Prediction("id","test",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, false));
        result = level.isFinished();
        Assert.assertFalse(result);

        level.resetPredictions();

        level.addPrediction(new Prediction("id","test",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, true));
        result = level.isFinished();
        Assert.assertTrue(result);

        level.resetPredictions();
    }

    @Test
    public void answerExistsTest() {
        boolean result;

        level.addPrediction(new Prediction("id","trouver",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, true));
        level.addPrediction(new Prediction("id","test",40.0, true));
        result = level.answerExists("trouver");
        Assert.assertTrue(result);
        result = level.answerExists("non trouvé");
        Assert.assertFalse(result);

        level.resetPredictions();
    }

    @Test
    public void answerHasAlreadyBeenFoundTest() {
        boolean result;

        level.addPrediction(new Prediction("id","devinée",40.0, true));
        level.addPrediction(new Prediction("id","non devinée",40.0, false));
        level.addPrediction(new Prediction("id","test",40.0, true));
        result = level.answerHasAlreadyBeenFound("devinée");
        Assert.assertTrue(result);
        result = level.answerHasAlreadyBeenFound("non devinée");
        Assert.assertFalse(result);

        level.resetPredictions();
    }

    @Test
    public void getPredictionTest() {
        Prediction p1 = new Prediction ("1","pred1",40.0, true);
        Prediction p2 = new Prediction ("2","pred2",40.0, true);
        level.addPrediction(p1);
        level.addPrediction(p2);

        Prediction found = level.getPrediction("pred1");

        Assert.assertEquals(p1, found);

        level.resetPredictions();
    }
}
