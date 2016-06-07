package com.github.migi_1.Context.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class ScoreReader {

    public static ArrayList<Score> read(String infile) throws IOException {
        ArrayList<Score> scores = new ArrayList<Score>();
        Iterator<String> lines = Files.readAllLines(Paths.get(infile)).iterator();
        if (lines.hasNext()) {
            lines.next();
            String tag;
            while ((tag = lines.next()).equals("<score>")) {
                System.out.println("hoi");
                scores.add(Score.read(lines));
            }

        }


        return scores;
    }

}
