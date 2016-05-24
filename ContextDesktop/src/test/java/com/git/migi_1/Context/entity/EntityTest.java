package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.scene.Spatial;

public abstract class EntityTest {

    private Spatial model;

    private MoveBehaviour moveBehaviour;

    private Entity testEntity;

    @Before
    public abstract void setUp();

    @Test
    public void testGetMoveBehaviour() {
        assertEquals(moveBehaviour, testEntity.getModel());
    };

}
