package com.github.migi_1.Context.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ScoreWriter {

    public void write(ArrayList<Score> scores, String infile) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(Paths.get(infile).toAbsolutePath().toString());
//        BufferedWriter writer = new BufferedWriter(new FileWriter(infile));
        List<String> strings = new ArrayList<String>();
        strings.add("<scores>");
        for (int i = 0; i < scores.size(); i++) {
            strings.add("<score>");
            strings.add("<name>");
            strings.add(scores.get(i).getName());
            strings.add("</name>");
            strings.add("<scoreValue>" + scores.get(i).getScore() + "</scoreValue>");
            strings.add("</score>");
        }
        strings.add("</scores>");
        Files.write(Paths.get(infile), strings, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);

    }

}
