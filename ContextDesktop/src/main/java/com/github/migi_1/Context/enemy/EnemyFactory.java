package com.github.migi_1.Context.enemy;

import com.github.migi_1.Context.model.entity.Carrier;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;



/**
 * This class is a factory that produces Enemy objects.
 * @author Damian
 *
 */
public class EnemyFactory {
    
    private BoundingBox levelPieceBoundingBox;
    private float levelPieceLength;
    private float levelPieceWidth;
    private Carrier[] carriers;

    public EnemyFactory(BoundingBox levelPieceBoundingBox, float levelPieceLength,
            float levelPieceWidth, Carrier[] carriers) {
        this.levelPieceBoundingBox = levelPieceBoundingBox;
        this.levelPieceLength = levelPieceLength;
        this.levelPieceWidth =  levelPieceWidth;
        this.carriers = carriers;
    }
    
    public Enemy createEnemy1(double currentLevelPiece) {
        Enemy enemy =  new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength),
                carriers[0].getModel().getLocalTranslation().y,
                levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)),
                carriers); 
        enemy.getModel().rotate(0, (float) Math.PI, 0);
        return enemy;
    }
    
    public Enemy createEnemy2(double currentLevelPiece) {
        Enemy enemy =  new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                + levelPieceLength * 1 / 2, carriers[0].getModel().getLocalTranslation().y,
                levelPieceWidth + (levelPieceBoundingBox.getZExtent() / 2)),
                carriers);        
        enemy.getModel().rotate(0, (float) Math.PI, 0);
        return enemy;
    }
    
    public Enemy createEnemy3(double currentLevelPiece) {
        return new Enemy(new Vector3f(-(((int) currentLevelPiece + 2) * levelPieceLength)
                + levelPieceLength * 1 / 2, carriers[0].getModel().getLocalTranslation().y,
                levelPieceWidth - (levelPieceBoundingBox.getZExtent() / 2)),
                carriers); 
    }



}
