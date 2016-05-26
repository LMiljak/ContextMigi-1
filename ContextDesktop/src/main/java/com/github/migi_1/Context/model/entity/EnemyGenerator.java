package com.github.migi_1.Context.model.entity;

import java.util.LinkedList;

import com.github.migi_1.Context.model.LevelPiece;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class EnemyGenerator {
    
    private LinkedList<Enemy> enemies;
    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    
    public EnemyGenerator() {
        enemies = new LinkedList<Enemy>();
        levelPieceBoundingBox = (BoundingBox) (new LevelPiece()).getModel().getWorldBound();
        levelPieceLength = levelPieceBoundingBox.getXExtent();
    }
    
    public LinkedList<Enemy> generateEnemies(Vector3f commanderLocation) {
        double currentLevelPiece = Math.floor(commanderLocation.x/levelPieceLength);
        System.out.println(currentLevelPiece);
        return null;        
    }
}
