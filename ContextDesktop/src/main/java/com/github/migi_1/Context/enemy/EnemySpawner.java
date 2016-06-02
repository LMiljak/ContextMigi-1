package com.github.migi_1.Context.enemy;

import java.util.LinkedList;

import com.github.migi_1.Context.model.LevelPiece;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class EnemySpawner {
    
    private EnemyFactory enemyFactory;
    private LinkedList<Enemy> enemies;
    private LinkedList<Enemy> deleteList;
    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    private double currentLevelPiece;
    private double lastLevelPiece;
    private float levelPieceWidth;
    private Vector3f commanderLocation;

    public EnemySpawner(Commander commander, Carrier[] carriers) {
        enemies = new LinkedList<Enemy>();
        deleteList = new LinkedList<Enemy>();        
        commanderLocation = commander.getModel().getLocalTranslation(); 
        levelPieceBoundingBox = (BoundingBox) (new LevelPiece()).getModel().getWorldBound();
        levelPieceLength = levelPieceBoundingBox.getXExtent();
        levelPieceWidth =  levelPieceBoundingBox.getCenter().z;
        currentLevelPiece = 0;
        lastLevelPiece = -1;
        enemyFactory = new EnemyFactory(levelPieceBoundingBox, levelPieceLength, levelPieceWidth, carriers);
    }

    public LinkedList<Enemy> generateEnemies() {
        currentLevelPiece = -Math.floor(commanderLocation.x / levelPieceLength);
        LinkedList<Enemy> newEnemies = new LinkedList<Enemy>();
        if (enemies.size() < 12) {
            if (currentLevelPiece != lastLevelPiece) {
                lastLevelPiece = currentLevelPiece;              

                double random = Math.random();
                if (random > 0.25 && random < 0.6) {
                    newEnemies.add(enemyFactory.createEnemy3(currentLevelPiece));
                } else if (random > 0.6 && random < 0.85) {
                    newEnemies.add(enemyFactory.createEnemy1(currentLevelPiece));
                    newEnemies.add(enemyFactory.createEnemy3(currentLevelPiece));
                } else {
                    newEnemies.add(enemyFactory.createEnemy1(currentLevelPiece));
                    newEnemies.add(enemyFactory.createEnemy2(currentLevelPiece));
                    newEnemies.add(enemyFactory.createEnemy3(currentLevelPiece));
                }            
            }
        }
        enemies.addAll(newEnemies);
        return newEnemies;        
    }

    public LinkedList<Enemy> deleteEnemies() {
        for (Enemy enemy : enemies) {
            if (enemy.getHealth() <= 0) {
                deleteList.add(enemy);
            }
            if (enemy.getModel().getLocalTranslation().distance(commanderLocation) > levelPieceLength * 3 
                    && enemy.getModel().getLocalTranslation().x > commanderLocation.x) {
                deleteList.add(enemy);
            }
        }
        return deleteList;
    }
}
