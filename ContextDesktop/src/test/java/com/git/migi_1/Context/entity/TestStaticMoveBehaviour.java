package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.StaticMoveBehaviour;
import com.jme3.math.Vector3f;

public class TestStaticMoveBehaviour {


    private StaticMoveBehaviour testMoveBehaviour;

    @Before
    public void setUp() {
        testMoveBehaviour = new StaticMoveBehaviour();

    }

    @Test
    public void testGetMoveBehaviour() {
        assertEquals(testMoveBehaviour.getMoveVector(), new Vector3f(0, 0, 0));
    }

}
