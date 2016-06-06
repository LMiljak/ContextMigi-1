package com.github.migi_1.Context.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreWriter {

    public static void write(ArrayList<Score> scores, String infile) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(infile));
        writer.write("<scores>\n");
        for (int i = 0; i < scores.size(); i++) {
            writer.write("<score>");
            writer.write("<name>\n" + scores.get(i).getName() + "\n</name>\n");
            writer.write("<scoreValue>\n" + scores.get(i).getScore() + "\n</scoreValue>\n");
            writer.write("</score>");
        }
        writer.write("</scores>");
        writer.close();
    }

}
