package com.github.migi_1.Context.model.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.github.migi_1.Context.model.entity.behaviour.MoveBehaviour;

/**
 * Test class for the Entity super class.
 * @author Marcel
 *
 */
public abstract class TestEntity {

    private MoveBehaviour moveBehaviour;
    private Entity testEntity;

    /**
     * Initialise attributes.
     * @throws Exception
     */
    @Before
    public abstract void setUp() throws Exception;

    /**
     * Test the getMoveBehaviour method.
     */
    @Test
    public void testGetMoveBehaviour() {
        testEntity.setMoveBehaviour(moveBehaviour);
        assertEquals(moveBehaviour, testEntity.getMoveBehaviour());
    };

    /**
     * Sets the moveBehaviour attribute.
     * @param moveBehaviour MoveBehaviour attribute
     */
    public void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        this.moveBehaviour = moveBehaviour;
    }

    /**
     * Sets the entity.
     * @param testEntity entity to set
     */
    public void setEntity(Entity testEntity) {
        this.testEntity = testEntity;
    }

}
