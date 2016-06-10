package com.github.migi_1.Context.obstacle;

import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

public class MovingObstacleMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;

    public MovingObstacleMoveBehaviour(MovingObstacle obstacle, BoundingBox leftBound, BoundingBox rightBound) {
        System.out.println("goedendag");
        this.moveVector = new Vector3f(0, 0, -2f);
    }

    @Override
    public void updateMoveVector() {
        // TODO Auto-generated method stub

    }

    @Override
    public Vector3f getMoveVector() {
        return moveVector;
    }

}
