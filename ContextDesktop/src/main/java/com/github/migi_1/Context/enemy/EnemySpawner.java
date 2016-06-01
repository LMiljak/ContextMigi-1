package com.github.migi_1.Context.enemy;

import java.util.LinkedList;

import com.github.migi_1.Context.model.LevelPiece;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class EnemySpawner {

    private LinkedList<Enemy> enemies;
    private LinkedList<Enemy> deleteList;
    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    private double currentLevelPiece;
    private double lastLevelPiece;
    private float levelPieceWidth;
    private Vector3f commanderLocation;
    private Carrier[] carriers;

    public EnemySpawner(Commander commander, Carrier[] carriers) {
        enemies = new LinkedList<Enemy>();
        deleteList = new LinkedList<Enemy>();
        this.carriers = carriers;
        commanderLocation = commander.getModel().getLocalTranslation(); 
        levelPieceBoundingBox = (BoundingBox) (new LevelPiece()).getModel().getWorldBound();
        levelPieceLength = levelPieceBoundingBox.getXExtent();
        levelPieceWidth =  levelPieceBoundingBox.getCenter().z;
        currentLevelPiece = 0;
        lastLevelPiece = -1;
    }

    public LinkedList<Enemy> generateEnemies() {
        currentLevelPiece = -Math.floor(commanderLocation.x/levelPieceLength);
        LinkedList<Enemy> newEnemies = new LinkedList<Enemy>();
        if (currentLevelPiece != lastLevelPiece) {
            lastLevelPiece = currentLevelPiece;              

            double random = Math.random();
            if(random > 0.25 && random < 0.6){
                newEnemies.add(createEnemy3());
            } else if(random > 0.6 && random < 0.85) {
                newEnemies.add(createEnemy1());
                newEnemies.add(createEnemy3());
            } else {
                newEnemies.add(createEnemy1());
                newEnemies.add(createEnemy2());
                newEnemies.add(createEnemy3());
            }            
        }
        enemies.addAll(newEnemies);
        return newEnemies;        
    }

    private Enemy createEnemy1() {
        Enemy enemy =  new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength),
                carriers[0].getModel().getLocalTranslation().y,
                levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)),
                carriers); 
        enemy.getModel().rotate(0, (float) Math.PI, 0);
        return enemy;
    }

    private Enemy createEnemy2() {
        Enemy enemy =  new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                + levelPieceLength * 1/2, carriers[0].getModel().getLocalTranslation().y,
                levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)),
                carriers);        
        enemy.getModel().rotate(0, (float) Math.PI, 0);
        return enemy;
    }

    private Enemy createEnemy3() {
        return new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                + levelPieceLength * 1/2, carriers[0].getModel().getLocalTranslation().y,
                levelPieceWidth - (levelPieceBoundingBox.getZExtent() / 2)),
                carriers);  
    }

    public LinkedList<Enemy> deleteEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.getModel().getLocalTranslation().distance(commanderLocation) > levelPieceLength * 3 &&
                    enemy.getModel().getLocalTranslation().x > commanderLocation.x)
            deleteList.add(enemy);
        }
        return deleteList;
    }
}
