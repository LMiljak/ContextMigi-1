package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.AcceleratorMoveBehaviour;
import com.jme3.math.Vector3f;

public class TestAcceleratorMoveBehaviour {

    private AcceleratorMoveBehaviour testMoveBehaviour;

    @Before
    public void setUp() {
        testMoveBehaviour = new AcceleratorMoveBehaviour();

    }

    @Test
    public void testGetMoveBehaviour() {
        assertEquals(testMoveBehaviour.getMoveVector(), new Vector3f(-0.2f, 0, 0));
    }

}
