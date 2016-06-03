package com.github.migi_1.Context.model.entity.behaviour;

import com.jme3.math.Vector3f;

/**
 * Movebehaviour class for Objects that accelerate over time.
 *
 */
public class AcceleratingMoveBehaviour extends EntityMoveBehaviour {

    /**
     * Constructor for the AcceleratingMoveBehaviour.
     * @param moveVector the starting move vector.
     */
    public AcceleratingMoveBehaviour(Vector3f moveVector) {
        setMoveVector(moveVector);
    }

    @Override
    public void updateMoveVector() {
        Vector3f currentMoveVector = getMoveVector();
        float newX = currentMoveVector.getX() - getAcceleratingFactor();
        setMoveVector(currentMoveVector.setX(newX));
    }

    @Override
    public void collided() {
        //Do nothing when a collision with the platform occurs.
    }

}
