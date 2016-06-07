package com.github.migi_1.Context.utility;

import java.util.Iterator;

public class Score {

    private String name;

    private int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public static Score read(Iterator<String> lines) {
        lines.next();
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
