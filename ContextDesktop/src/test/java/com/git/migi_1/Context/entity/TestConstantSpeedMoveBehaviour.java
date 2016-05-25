package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.ConstantSpeedMoveBehaviour;
import com.jme3.math.Vector3f;

/**
 * Test class for the ConstantSpeedMoveBehaviour class.
 * @author Marcel
 *
 */
public class TestConstantSpeedMoveBehaviour {

    /**
     * Instance of tested class.
     */
    private ConstantSpeedMoveBehaviour testMoveBehaviour;
    
    private Vector3f moveVector = new Vector3f(1, 2, 3);

    /**
     * Initialises the tested object.
     */
    @Before
    public void setUp() {
        testMoveBehaviour = new ConstantSpeedMoveBehaviour(moveVector);
    }

    /**
     * Tests the getMoveBehaviour method.
     */
    @Test
    public void testGetMoveBehaviour() {
        assertEquals(moveVector, testMoveBehaviour.getMoveVector());
    }

}
