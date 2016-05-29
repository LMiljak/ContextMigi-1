package com.git.migi_1.Context.entity.behaviour;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.behaviour.StaticMoveBehaviour;
import com.jme3.math.Vector3f;

/**
 * Test class for the StaticMoveBehaviour class.
 * @author Marcel
 *
 */
public class TestStaticMoveBehaviour {


    private StaticMoveBehaviour testMoveBehaviour;

    /**
     * Initialise the tested object.
     */
    @Before
    public void setUp() {
        testMoveBehaviour = new StaticMoveBehaviour();

    }

    /**
     * Test the getMoveBehaviour method.
     */
    @Test
    public void testGetMoveBehaviour() {
        assertEquals(testMoveBehaviour.getMoveVector(), new Vector3f(0, 0, 0));
    }

}
