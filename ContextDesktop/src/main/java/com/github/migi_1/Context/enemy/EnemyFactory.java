package com.github.migi_1.Context.enemy;

import java.util.ArrayList;

import com.github.migi_1.Context.model.LevelPiece;
import com.github.migi_1.Context.model.entity.Carrier;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

/**
 * This class is a factory that produces Enemy objects.
 * @author Damian
 *
 */
public class EnemyFactory {

    private static final float SPAWN_HEIGHT = -17.5f;

    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    private float levelPieceWidth;
    private ArrayList<Carrier> carriers;


    /**
     * Constructor of the EnemyFactory.
     * @param carriers the carriers on which the spawning will be based.
     */
    public EnemyFactory(ArrayList<Carrier> carriers) {
        LevelPiece levelPiece = new LevelPiece();
        this.levelPieceBoundingBox = ((BoundingBox) levelPiece.getModel().getWorldBound());
        this.levelPieceLength = levelPieceBoundingBox.getXExtent();
        this.levelPieceWidth = levelPieceBoundingBox.getCenter().z;
        this.carriers = carriers;
    }

    /**
     * Creates an enemy at the left side of the path, 2 levelPieces ahead of the commander.
     * @param currentLevelPiece to calculate where to spawn the enemy
     * @return the created enemy.
     */
    public Enemy createEnemy1(double currentLevelPiece) {
        Enemy enemy =  new Enemy(new Vector3f(
                -(((int) currentLevelPiece + 2) * levelPieceLength),
                SPAWN_HEIGHT,
                levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)),
                carriers); 
        return enemy;
    }

    /**
     * Creates an enemy at the left side of the path, 2 levelPieces ahead of the commander.
     * @param currentLevelPiece to calculate where to spawn the enemy
     * @return the created enemy.
     */
    public Enemy createEnemy2(double currentLevelPiece) {
        Enemy enemy =  new Enemy(new Vector3f(
                -(((int) currentLevelPiece + 2) * levelPieceLength)
                + levelPieceLength * 1 / 2,
                SPAWN_HEIGHT,
                levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)),
                carriers);        

        return enemy;
    }

    /**
     * Creates an enemy at the left side of the path, 2.5 levelPieces ahead of the commander.
     * @param currentLevelPiece to calculate where to spawn the enemy
     * @return the created enemy.
     */
    public Enemy createEnemy3(double currentLevelPiece) {
        Enemy enemy = new Enemy(new Vector3f(
                -(((int) currentLevelPiece + 2) * levelPieceLength)
                + levelPieceLength * 1 / 2,
                SPAWN_HEIGHT,
                levelPieceWidth - (levelPieceBoundingBox.getZExtent() / 2)),
                carriers); 
        enemy.getModel().rotate(0, (float) Math.PI, 0);
        return enemy;
    }




}
