package com.github.migi_1.Context.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreReader {

    public static ArrayList<Score> read(String infile) {
        ArrayList<Score> scores = new ArrayList<Score>();
        try {

            File scoreFile = new File(infile);
            if (!scoreFile.exists()) {
                return scores;
            }
            Scanner scanner = new Scanner(scoreFile);
            if (scanner.hasNext()) {
                scanner.nextLine();
                scores.add(Score.read(scanner));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scores;
    }

}
