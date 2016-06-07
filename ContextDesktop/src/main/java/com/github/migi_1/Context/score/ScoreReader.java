package com.github.migi_1.Context.score;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class handles the reading of the score file.
 * @author Marcel
 *
 */
public class ScoreReader {

    /**
     * Read the file located at the given location.
     * @param infile File location.
     * @return List of score objects
     * @throws IOException Exception while reading file
     */
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
