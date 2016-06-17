package com.github.migi_1.Context.score;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class handles the writing and reading of files.
 *
 * @author Marcel
 *
 */
public class ScoreController {
	
	private static final String SCORE_FILE = System.getProperty("user.dir") + "/assets/Scores/scores.txt";
	private static final ScoreController INSTANCE = new ScoreController();
	
    private ArrayList<Score> scores;
    private ScoreReader reader;
    private ScoreWriter writer;

    /**
     * Gets the ScoreController instance.
     * 
     * @return
     * 		The ScoreController instance.
     */
    public static ScoreController getInstance() {
    	return INSTANCE;
    }
    
    /**
     * Constructor.
     */
    private ScoreController() {

        reader = new ScoreReader();
        writer = new ScoreWriter();

        try {
            scores = reader.read(SCORE_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Add a score.
     * @param score Score object to add.
     */
    public void addScore(Score score) {
        scores.add(score);
        try {
            writer.write(scores, SCORE_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the high score.
     * @return the high score
     */
    public int getHighScore() {
        int highest = 0;
        for (Score score: scores) {
            if (score.getScore() > highest) {
                highest = score.getScore();
            }
        }
        return highest;
    }


}
