package com.github.migi_1.Context.enemy;

import java.util.LinkedList;

import com.github.migi_1.Context.model.LevelPiece;
import com.github.migi_1.Context.model.entity.Carrier;
import com.github.migi_1.Context.model.entity.Commander;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class EnemySpawner {
    
    private static final int ENEMYHEIGHT = -18;
    private Commander commander;
    private LinkedList<Enemy> enemies;
    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    private double currentLevelPiece;
    private double lastLevelPiece;
    private float levelPieceWidth;
    private Vector3f commanderLocation;
    private Carrier[] carriers;

    public EnemySpawner(Commander commander, Carrier[] carriers) {
        enemies = new LinkedList<Enemy>();
        this.commander = commander;
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
        if (currentLevelPiece != lastLevelPiece) {
            lastLevelPiece = currentLevelPiece;
            
            Enemy enemy1 = new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength), ENEMYHEIGHT,
                    levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)), commander, carriers);            
            Enemy enemy2 = new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                    + levelPieceLength * 1/2, ENEMYHEIGHT,
                    levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)), commander, carriers);
            Enemy enemy3 = new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                    + levelPieceLength * 1/2, ENEMYHEIGHT,
                    levelPieceWidth - (levelPieceBoundingBox.getZExtent() / 2)), commander, carriers);  
            
            enemy1.getModel().rotate(0, (float) Math.PI, 0);
            enemy2.getModel().rotate(0, (float) Math.PI, 0);
            
            double random = Math.random();
            if(random > 0.25 && random < 0.6){
                enemies.add(enemy3);
            } else if(random > 0.6 && random < 0.85) {
                enemies.add(enemy1);
                enemies.add(enemy3);
            } else {
                enemies.add(enemy1);
                enemies.add(enemy2);
                enemies.add(enemy3);
            }            
        }
        return enemies;        
    }
}
