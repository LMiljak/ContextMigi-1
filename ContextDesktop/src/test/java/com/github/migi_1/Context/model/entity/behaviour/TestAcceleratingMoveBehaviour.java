package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.jme3.math.Vector3f;

/**
 * Test suite for the accelerating move behaviour.
 */
public class TestAcceleratingMoveBehaviour {
    private AcceleratingMoveBehaviour accMoveBehaviour;
    private Vector3f vector;

    /**
     * Setup for the tests.
     */
    @Before
    public void setUp() {
        vector = new Vector3f(0, 0, 0);
        accMoveBehaviour = Mockito.spy(new AcceleratingMoveBehaviour(vector));
    }

    /**
     * Tests the updating of the move vector.
     */
    @Test
    public void testUpdateMoveVector() {
        float oldX = accMoveBehaviour.getMoveVector().getX();
        accMoveBehaviour.updateMoveVector();
        assertNotEquals(oldX, accMoveBehaviour.getMoveVector().x);
    }

    /**
     * Verifies a collision with an object with this move behaviour
     * doesn't do anything.
     */
    @Test
    public void testCollisionUnused() {
        accMoveBehaviour.collided();
        Mockito.verify(accMoveBehaviour).collided();
        Mockito.verifyNoMoreInteractions(accMoveBehaviour);
    }
}
