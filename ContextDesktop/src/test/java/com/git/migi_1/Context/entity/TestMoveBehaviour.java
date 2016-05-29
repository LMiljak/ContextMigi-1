package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.math.Vector3f;

public abstract class TestMoveBehaviour {

    private MoveBehaviour moveBehaviour;
    private Vector3f moveVector;

    /**
     * Initialise attributes.
     */
    @Before
    public abstract void setUp();

    @Test
    public void testGetAndSetMoveVector() {

        moveBehaviour.setMoveVector(moveVector);
        assertEquals(moveVector, moveBehaviour.getMoveVector());
    }

    public MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    public Vector3f getMoveVector() {
        return moveVector;
    }

    public void setMoveVector(Vector3f moveVector) {
        this.moveVector = moveVector;
    }



}
