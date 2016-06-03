package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector3f;

/**
 * Test class for the MoveBehaviour class.
 * @author Marcel
 *
 */
public abstract class TestMoveBehaviour {

    private MoveBehaviour moveBehaviour;
    private Vector3f moveVector;

    /**
     * Initialise attributes.
     */
    @Before
    public abstract void setUp();

    /**
     * Test the getter and setter.
     */
    @Test
    public void testGetAndSetMoveVector() {

        moveBehaviour.setMoveVector(moveVector);
        assertEquals(moveVector, moveBehaviour.getMoveVector());
    }

    /**
     * Get the MoveBehaviour.
     * @return moveBehaviour
     */
    public MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    /**
     * Set the MoveBehaviour.
     * @param moveBehaviour MoveBehaviour to set
     */
    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    /**
     * Get the moveVector.
     * @return moveVector
     */
    public Vector3f getMoveVector() {
        return moveVector;
    }

    /**
     * Set the moveVector.
     * @param moveVector vector to set
     */
    public void setMoveVector(Vector3f moveVector) {
        this.moveVector = moveVector;
    }

}
