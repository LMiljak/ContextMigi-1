package com.github.migi_1.Context.model.entity;

import java.util.LinkedList;

import com.github.migi_1.Context.model.LevelPiece;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class EnemyGenerator {
    
    private LinkedList<Enemy> enemies;
    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    private double currentLevelPiece;
    private double lastLevelPiece;

    public EnemyGenerator() {
        enemies = new LinkedList<Enemy>();
        levelPieceBoundingBox = (BoundingBox) (new LevelPiece()).getModel().getWorldBound();
        levelPieceLength = levelPieceBoundingBox.getXExtent();
        currentLevelPiece = 0;
        lastLevelPiece = -1;
    }

    public LinkedList<Enemy> generateEnemies(Vector3f commanderLocation) {
        currentLevelPiece = -Math.floor(commanderLocation.x/levelPieceLength);
       // System.out.println(currentLevelPiece);
        if(currentLevelPiece != lastLevelPiece){
            lastLevelPiece = currentLevelPiece;
            double random = Math.random();
            if(random < 0.5 ) {
                System.out.println("one");
                enemies.add(new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength), -16, 2)));
            } else if(random < 0.8 && random > 0.5) {
                System.out.println("two");
                enemies.add(new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength), -16, 2)));
                enemies.add(new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                        + levelPieceLength * 1/2, -16, 2)));
            } else {
                System.out.println("three");
                enemies.add(new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength), -16, 2)));
                enemies.add(new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                        + levelPieceLength * 1/2, -16, 2)));
                enemies.add(new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                        + levelPieceLength * 1/2, -16, -7)));
            }            
        }
        return enemies;        
    }
}
