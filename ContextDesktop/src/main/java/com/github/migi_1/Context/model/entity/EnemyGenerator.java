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
            enemies.add(new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength), -20, 2)));
            System.out.println("Commander : " + commanderLocation);
            System.out.println("Enemy : " + enemies.getLast().getModel().getLocalTranslation().x);
        }
        return enemies;        
    }
}
