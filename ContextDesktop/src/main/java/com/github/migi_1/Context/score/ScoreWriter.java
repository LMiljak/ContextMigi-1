package com.github.migi_1.Context.score;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles the writing of the score file.
 * @author Marcel
 *
 */
public class ScoreWriter {

    /**
     * Write a list of Score objects to a given file.
     * @param scores Score objects to write
     * @param infile File location
     * @throws IOException Exception while reading file
     */
    public void write(ArrayList<Score> scores, String infile) throws IOException {
        List<String> strings = new ArrayList<String>();
        strings.add("<scores>");
        for (int i = 0; i < scores.size(); i++) {
            strings.add("<score>");
            strings.add("<name>");
            strings.add(scores.get(i).getName());
            strings.add("</name>");
            strings.add("<scoreValue>");
            strings.add(String.valueOf(scores.get(i).getScore()));
            strings.add("</scoreValue>");
            strings.add("</score>");
        }
        strings.add("</scores>");

        //if the file does not exist, create it
        if (!Files.exists(Paths.get(infile))) {
            Files.createFile(Paths.get(infile));
        }

        //write scores to file
        Files.write(Paths.get(infile), strings, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE);
    }
}
