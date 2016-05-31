package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.ConstantSpeedMoveBehaviour;
import com.jme3.math.Vector3f;

/**
 * Test class for the ConstantSpeedMoveBehaviour class.
 * @author Marcel
 *
 */
public class TestConstantSpeedMoveBehaviour extends TestEntityMoveBehaviour {

    private ConstantSpeedMoveBehaviour testMoveBehaviour;

    /**
     * Initialises the tested object.
     */
    @Override
    @Before
    public void setUp() {
        setMoveVector(new Vector3f(1, 2, 3));
        testMoveBehaviour = new ConstantSpeedMoveBehaviour(getMoveVector());
        setMoveBehaviour(testMoveBehaviour);
    }

    /**
     * Tests the getMoveBehaviour method.
     */
    @Test
    public void testGetMoveBehaviour() {
        assertEquals(getMoveVector(), getMoveBehaviour().getMoveVector());
    }

    /**
     * Test the collided method.
     */
    @Override
    @Test
    public void collidedTest() {
        assertTrue(Math.abs(testMoveBehaviour.getDecay() - 1.0f) < 0.01);
        testMoveBehaviour.collided();
        assertTrue(Math.abs(testMoveBehaviour.getDecay()) < 0.01);
    }

    /**
     * Test the updateMoveVector method.
     */
    @Test
    public void updateMoveVectorTest() {
        testMoveBehaviour.setDecay(0.4f);
        assertTrue(Math.abs(testMoveBehaviour.getDecay() - 0.4f) < 0.01);
        testMoveBehaviour.updateMoveVector();
        assertTrue(Math.abs(testMoveBehaviour.getDecay() - 0.41f) < 0.01);
    }


}
