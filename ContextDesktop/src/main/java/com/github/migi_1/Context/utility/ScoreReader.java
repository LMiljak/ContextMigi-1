package com.github.migi_1.Context.utility;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ScoreReader {

    public ArrayList<Score> read(String infile) {
        ArrayList<Score> scores = new ArrayList();
        try {

            File scoreFile = new File(infile);
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(scoreFile);
            NodeList scoreList = doc.getElementsByTagName("score");
            for (int i = 0; i < scoreList.getLength(); i++) {
                Node node = scoreList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getAttribute("name");
                    int scoreValue = Integer.parseInt(element.getAttribute("scoreVale"));
                    scores.add(new Score(name, scoreValue));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scores;
    }

}
