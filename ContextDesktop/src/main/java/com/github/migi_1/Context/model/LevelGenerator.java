package com.github.migi_1.Context.model;

import java.util.LinkedList;

import com.jme3.math.Vector3f;

public class LevelGenerator {

    private static final int LEVEL_PIECES = 5;

    private LinkedList<LevelPiece> levelPieces;

    private Vector3f locationNextPiece;

    public LevelGenerator(Vector3f locationNextPiece) {
       levelPieces = new LinkedList<LevelPiece>();
       this.locationNextPiece = locationNextPiece;
    }

    public LinkedList<LevelPiece> getLevelPieces() {

        while (levelPieces.size() < LEVEL_PIECES) {
            LevelPiece levelPiece = new LevelPiece();
            levelPieces.add(levelPiece);
        }
        return levelPieces;
    }

}
