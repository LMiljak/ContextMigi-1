package com.github.migi_1.Context.obstacle;

import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class MovingObstacleMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;

    private boolean goingLeft;

    private float leftBound;

    private float rightBound;

    private MovingObstacle movingObstacle;

    private final Vector3f baseVector = new Vector3f(0, 0, 0.05f);

    public MovingObstacleMoveBehaviour(MovingObstacle movingObstacle, BoundingBox leftBound, BoundingBox rightBound) {
        this.moveVector = baseVector;
        this.goingLeft = true;
        this.leftBound = getBound(leftBound);
        this.rightBound = getBound(rightBound);
        this.movingObstacle = movingObstacle;
    }

    @Override
    public void updateMoveVector() {
        if (movingObstacle.getModel().getLocalTranslation().z > leftBound) {
            goingLeft = false;
            moveVector = baseVector.mult(-1);
        }
        if (movingObstacle.getModel().getLocalTranslation().z < rightBound) {
            goingLeft = true;
            moveVector = baseVector;
        }
        System.out.println(movingObstacle.getModel().getLocalTranslation().z);
        System.out.println(leftBound);
        System.out.println(rightBound);
    }

    @Override
    public Vector3f getMoveVector() {
        return moveVector;
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
