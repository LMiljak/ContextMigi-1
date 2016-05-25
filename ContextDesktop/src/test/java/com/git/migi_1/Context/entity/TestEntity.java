package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.MoveBehaviour;
import com.jme3.scene.Spatial;

/**
 * Test class for the Entity super class.
 * @author Marcel
 *
 */
public abstract class TestEntity {

    private Spatial model;
    private MoveBehaviour moveBehaviour;
    private Entity testEntity;

    /**
     * Initialise attributes.
     */
    @Before
    public abstract void setUp();

    /**
     * Test the getMoveBehaviour method.
     */
    @Test
    public void testGetMoveBehaviour() {
        testEntity.setMoveBehaviour(moveBehaviour);
        assertEquals(moveBehaviour, testEntity.getMoveBehaviour());
    };

    /**
     * Sets the model attribute.
     * @param model Model attribute
     */
    public void setModel(Spatial model) {
        this.model = model;
    }

    /**
     * Sets the moveBehaviour attribute.
     * @param moveBehaviour MoveBehaviour attribute
     */
    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    /**
     * Set the entity.
     * @param testEntity entity to set
     */
    public void setEntity(Entity testEntity) {
        this.testEntity = testEntity;
    }

}
