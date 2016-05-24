package com.git.migi_1.Context.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.github.migi_1.Context.model.entity.Entity;
import com.jme3.math.Vector3f;


@PrepareForTest({Entity.class})
public class TestEntity {

    private Entity testEntity;

    @Before
    public void setUp() {
        testEntity = Mockito.spy(Entity.class);
    }

    @Test
    public void testGetMoveBehaviour() {
        assertEquals(testEntity.getMoveBehaviour().getMoveVector(), new Vector3f(0, 0, 0));
    }

}
