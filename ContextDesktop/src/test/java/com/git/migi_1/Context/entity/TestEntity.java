package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.scene.Spatial;

public abstract class TestEntity {

    private Spatial model;

    private MoveBehaviour moveBehaviour;

    private Entity testEntity;

    @Before
    public abstract void setUp();

    @Test
    public void testGetMoveBehaviour() {
        testEntity.setMoveBehaviour(moveBehaviour);
        assertEquals(moveBehaviour, testEntity.getMoveBehaviour());
    };

    public void setModel(Spatial model) {
        this.model = model;
    }

    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    public void setEntity(Entity testEntity) {
        this.testEntity = testEntity;
    }

}
