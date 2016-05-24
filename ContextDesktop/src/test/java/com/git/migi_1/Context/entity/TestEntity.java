package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.scene.Spatial;


@PrepareForTest({Entity.class})
public abstract class TestEntity {

    private Entity entity;

    private MoveBehaviour moveBehaviour;

    private Spatial model;

    @Before
    public abstract void setUp();

    protected void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    protected MoveBehaviour getMoveBehaviour() {
        return moveBehaviour;
    }

    protected void setModel(Spatial model) {
        this.model = model;
    }

    protected Spatial getModel() {
        return model;
    }

    @Test
    public void testGetMoveBehaviour() {
        assertEquals(moveBehaviour, entity.getMoveBehaviour());
    }

    @Test
    public void testSetMoveBehaviour(MoveBehaviour moveBehaviour) {
        entity.setMoveBehaviour(moveBehaviour);
        assertEquals(moveBehaviour, entity.getMoveBehaviour());

    }

    @Test
    public void testGetModel() {
        assertEquals(model, entity.getModel());
    }

    @Test
    public void testSetModel(Spatial model) {
        entity.setModel(model);
        assertEquals(model, entity.getModel());

    }
}
