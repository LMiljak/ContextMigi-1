package com.github.migi_1.Context.score;

import java.util.Iterator;

/**
 * This class implements a Score object.
 *
 * @author Marcel
 *
 */
public class Score {

    private String name;

    private int score;

    /**
     * Constructor.
     * @param name Name of the players.
     * @param score Score value.
     */
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Getter for the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name.
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the score.
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score.
     * @param score The score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Read out a score.
     * @param lines Iterator containing a Score object
     * @return The Score object.
     */
    public static Score read(Iterator<String> lines) {
        lines.next();
        String name = lines.next();
        lines.next();
        lines.next();
        int score = Integer.parseInt(lines.next());
        lines.next();
        lines.next();
        return new Score(name, score);
    }


}
