package com.github.migi_1.Context.utility;

import java.util.ArrayList;

public class ScoreController {

    private ArrayList<Score> scores;

    private static final String SCORE_FILE = System.getProperty("user.dir") + "/assets/Scores/scores.txt";

    private ScoreReader reader;

    private ScoreWriter writer;

    public ScoreController() {
        reader = new ScoreReader();
        writer = new ScoreWriter();

        try {
            scores = new ArrayList<Score>();
            scores.add(new Score("hoi", 10));
            writer.write(scores, SCORE_FILE);
            System.out.println(ScoreReader.read(SCORE_FILE));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
