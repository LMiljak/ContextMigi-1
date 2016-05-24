package com.git.migi_1.Context.entity;

import org.junit.Before;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.github.migi_1.Context.model.entity.Entity;
import com.github.migi_1.Context.model.entity.MoveBehaviour;


@PrepareForTest({Entity.class})
public abstract class TestEntity {

    private Entity testEntity;

    private MoveBehaviour testMoveBehaviour;

    @Before
    public abstract void setUp();

    protected void setMoveBehaviour(MoveBehaviour moveBehaviour) {
        testMoveBehaviour = moveBehaviour;
    }

}
