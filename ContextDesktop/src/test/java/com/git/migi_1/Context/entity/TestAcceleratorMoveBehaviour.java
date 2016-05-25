package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.AcceleratorMoveBehaviour;
import com.jme3.math.Vector3f;

/**
 * Test class for the AccelerometerMoveBehaviour class.
 * @author Marcel
 *
 */
public class TestAcceleratorMoveBehaviour {

    /**
     * Instance of tested class.
     */
    private AcceleratorMoveBehaviour testMoveBehaviour;

    /**
     * Initialise the tested object.
     */
    @Before
    public void setUp() {
        testMoveBehaviour = new AcceleratorMoveBehaviour();
    }

    /**
     * Test the getMoveBehaviour method.
     */
    @Test
    public void testGetMoveBehaviour() {
        assertEquals(testMoveBehaviour.getMoveVector(), new Vector3f(-0.2f, 0, 0));
    }

}
