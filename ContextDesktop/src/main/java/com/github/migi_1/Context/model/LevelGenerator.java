package com.github.migi_1.Context.model;

import java.util.LinkedList;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;

/**
 * Class responsible for generating and deleting the levelpieces
 * at the correct time.
 * @author Marcel
 *
 */
public class LevelGenerator {

    private static final int LEVEL_PIECES = 5;

    private LinkedList<LevelPiece> levelPieces;

    private Vector3f locationNextPiece;

    /**
     * Constructor of the levelGenerator.
     * @param locationNextPiece location of the next piece to be placed.
     */
    public LevelGenerator(Vector3f locationNextPiece) {
       levelPieces = new LinkedList<LevelPiece>();
       this.locationNextPiece = locationNextPiece;
    }

    /**
     * Checks how far away the player is from the last piece, it checks which pieces are
     * ready to be deleted and adds them to the delete list.
     * @param commanderLocation to check how far away the commander is from the last platform.
     * @return the list of pieces to delete.
     */
    public LinkedList<LevelPiece> deleteLevelPieces(Vector3f commanderLocation) {
        LinkedList<LevelPiece> deleteList = new LinkedList<LevelPiece>();
        Boolean done = false;
        while (!done) {
            if (levelPieces.size() > 0) {
                LevelPiece checkLevelPiece = levelPieces.peek();
                Vector3f v = checkLevelPiece.getModel().getLocalTranslation();
                Vector2f v1 = new Vector2f(v.getX(), v.getY());
                Vector2f v2 = new Vector2f(commanderLocation.x, commanderLocation.y);
                if (v1.distance(v2) > 100) {
                   deleteList.add(levelPieces.poll());
                }
                else {
                    done = true;
                }
           }
            else {
                done = true;
            }
        }

       return deleteList;
    }

    /**
     * Returns the levelpieces to add and which location to place them at.
     * @param commanderLocation to check if new pieces need to be added.
     * @return the list of pieces to be added.
     */
    public LinkedList<LevelPiece> getLevelPieces(Vector3f commanderLocation) {
        while (levelPieces.size() < LEVEL_PIECES) {
            LevelPiece levelPiece = new LevelPiece();
            levelPiece.move(locationNextPiece);
            levelPieces.add(levelPiece);
            BoundingBox bb = (BoundingBox) levelPiece.getModel().getWorldBound();
            //shift orientation to where the next level piece should spawn
            locationNextPiece.x -= 2 * bb.getXExtent() - 2.0f;
        }
        return levelPieces;
    }

    /**
     * Return the number of LevelPiece objects.
     * @return Value of LEVEL_PIECES attribute
     */
    public int getNumberOfLevelPieces() {
        return LEVEL_PIECES;
    }

    public void setLevelPieces(LinkedList<LevelPiece> levelPieces) {
        this.levelPieces = levelPieces;
    }

}
