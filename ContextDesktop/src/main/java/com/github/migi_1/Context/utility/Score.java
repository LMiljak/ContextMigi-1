package com.github.migi_1.Context.utility;

import java.util.Scanner;

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

    public static Score read(Scanner scanner) {
        scanner.nextLine();
        scanner.nextLine();
        String name = scanner.nextLine();
        scanner.nextLine();
        scanner.nextLine();
        int score = Integer.parseInt(scanner.nextLine());
        scanner.nextLine();
        scanner.nextLine();
        return new Score(name, score);
    }


}
