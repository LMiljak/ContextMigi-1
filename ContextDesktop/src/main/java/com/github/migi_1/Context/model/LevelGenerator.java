package com.github.migi_1.Context.model;

import java.util.LinkedList;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

public class LevelGenerator {

    private static final int LEVEL_PIECES = 5;

    private LinkedList<LevelPiece> levelPieces;

    private Vector3f locationNextPiece;

    public LevelGenerator(Vector3f locationNextPiece) {
       levelPieces = new LinkedList<LevelPiece>();
       this.locationNextPiece = locationNextPiece;
    }

    public LinkedList<LevelPiece> deleteLevelPieces(Vector3f commanderLocation) {
        LinkedList<LevelPiece> deleteList = new LinkedList<LevelPiece>();
        if (levelPieces.size() > 0) {
            LevelPiece checkLevelPiece = levelPieces.peek();
            BoundingBox bb1 = (BoundingBox) checkLevelPiece.getModel().getWorldBound();
            Vector2f v1 = new Vector2f(bb1.getCenter().x, bb1.getCenter().y);
            Vector2f v2 = new Vector2f(commanderLocation.x, commanderLocation.y);
            System.out.println(v1.distance(v2));
            if (v1.distance(v2) > 100) {
               deleteList.add(levelPieces.poll());
            }
       }
       return deleteList;
    }

    public LinkedList<LevelPiece> getLevelPieces(Vector3f commanderLocation) {



        while (levelPieces.size() < LEVEL_PIECES) {
            LevelPiece levelPiece = new LevelPiece();
            levelPiece.move(locationNextPiece);
            levelPieces.add(levelPiece);
            BoundingBox bb = (BoundingBox) levelPiece.getModel().getWorldBound();

            //shift orientation to where the next level piece should spawn
            locationNextPiece.x -= 2 * bb.getXExtent() - 2.0f;
        }
        return levelPieces;
    }

}
