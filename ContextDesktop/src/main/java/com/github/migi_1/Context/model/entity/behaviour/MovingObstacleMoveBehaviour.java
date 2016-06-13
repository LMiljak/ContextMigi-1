package com.github.migi_1.Context.model.entity.behaviour;

import com.github.migi_1.Context.obstacle.MovingObstacle;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

/**
 * Move behaviour for the MovingObstacle class.
 * @author Marcel
 *
 */
public class MovingObstacleMoveBehaviour extends MoveBehaviour {

    private float leftBound;

    private float rightBound;

    private MovingObstacle movingObstacle;

    private final Vector3f baseVector = new Vector3f(0, 0, 0.05f);

    /**
     * Constructor.
     * @param movingObstacle MovingObstacle to follow.
     * @param leftBound BoundingBox of the left side
     * @param rightBound BoundingBox of the right side
     */
    public MovingObstacleMoveBehaviour(MovingObstacle movingObstacle, BoundingBox leftBound, BoundingBox rightBound) {
        setMoveVector(baseVector);
        this.leftBound = getBound(leftBound);
        this.rightBound = getBound(rightBound);
        this.movingObstacle = movingObstacle;
    }

    @Override
    public void updateMoveVector() {

        //if exceeding bounds to the left, go to the right
        if (movingObstacle.getModel().getLocalTranslation().z > leftBound) {
            setMoveVector(baseVector.mult(-1));
        }
        //if exceeding bounds to the right, go to the left
        if (movingObstacle.getModel().getLocalTranslation().z < rightBound) {
            setMoveVector(baseVector);
        }
    }

    /**
     * Get the z coordinate of the center of a bounding box.
     * @param boundingBox Bounding box to check
     * @return z coordinate of the center
     */
    private float getBound(BoundingBox boundingBox) {
        return boundingBox.getCenter().z;
    }

}
