package com.github.migi_1.Context.model.entity;

import java.util.LinkedList;

import com.github.migi_1.Context.model.LevelPiece;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class EnemyGenerator {
    
    private static final int ENEMYHEIGHT = -16;
    private LinkedList<Enemy> enemies;
    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    private double currentLevelPiece;
    private double lastLevelPiece;
    private float levelPieceWidth;

    public EnemyGenerator() {
        enemies = new LinkedList<Enemy>();
        levelPieceBoundingBox = (BoundingBox) (new LevelPiece()).getModel().getWorldBound();
        levelPieceLength = levelPieceBoundingBox.getXExtent();
        levelPieceWidth =  levelPieceBoundingBox.getCenter().z;
        currentLevelPiece = 0;
        lastLevelPiece = -1;
    }

    public LinkedList<Enemy> generateEnemies(Vector3f commanderLocation) {
        currentLevelPiece = -Math.floor(commanderLocation.x/levelPieceLength);
       // System.out.println(currentLevelPiece);
        if(currentLevelPiece != lastLevelPiece){
            lastLevelPiece = currentLevelPiece;
            
            Enemy enemy1 = new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength), ENEMYHEIGHT,
                    levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)));
            Enemy enemy2 = new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                    + levelPieceLength * 1/2, ENEMYHEIGHT, levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)));
            Enemy enemy3 = new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                    + levelPieceLength * 1/2, ENEMYHEIGHT, levelPieceWidth - (levelPieceBoundingBox.getZExtent() / 2)));
            
            double random = Math.random();
            if(random > 0.3 && random < 0.6){
                enemies.add(enemy1);
            } else if(random > 0.6 && random < 0.85) {
                enemies.add(enemy1);
                enemies.add(enemy2);
            } else {
                enemies.add(enemy1);
                enemies.add(enemy2);
                enemies.add(enemy3);
            }            
        }
        return enemies;        
    }
}
