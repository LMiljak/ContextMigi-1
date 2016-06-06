package com.github.migi_1.Context.utility;

import java.util.ArrayList;

public class ScoreController {

    private ArrayList<Score> scores;

    private static final String SCORE_FILE = "/D:/Users/Marcel/scores.xml";

    public ScoreController() {
        try {
            scores = new ArrayList<Score>();
            scores.add(new Score("hoi", 10));
            ScoreWriter.write(scores, SCORE_FILE);
            System.out.println(ScoreReader.read(SCORE_FILE));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
