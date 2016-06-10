package com.github.migi_1.Context.obstacle;

import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;
import com.jme3.math.Vector3f;

public class MovingObstacleMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;

    public MovingObstacleMoveBehaviour() {
        this.moveVector = new Vector3f(0, 0, -0.5f);
    }

    @Override
    public void updateMoveVector() {
        // TODO Auto-generated method stub

    }

}
