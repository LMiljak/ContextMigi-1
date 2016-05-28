package com.github.migi_1.Context.model.entity;

import com.jme3.math.Vector3f;

public class CarrierMoveBehaviour extends MoveBehaviour {

    private Vector3f moveVector;

    private int immobalized;

    public CarrierMoveBehaviour(Vector3f moveVector) {
        this.moveVector = moveVector;
        this.immobalized = 0;
    }

    public void collided() {
        immobalized = 120;
    }

    @Override
    public Vector3f getMoveVector() {
        updateMoveVector();
        if (immobalized > 0) {
            return new Vector3f(0, 0, 0);
        }
        return moveVector;
    }

    @Override
    public void updateMoveVector() {
        if (immobalized > 0) {
            immobalized -= 1;
        }
    }

}
