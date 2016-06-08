package com.git.migi_1.Context.entity.behaviour;

import org.junit.Before;

import com.github.migi_1.Context.model.entity.behaviour.StaticMoveBehaviour;
import com.github.migi_1.Context.model.entity.behaviour.TestMoveBehaviour;

/**
 * Test class for the StaticMoveBehaviour class.
 * @author Marcel
 *
 */
public class TestStaticMoveBehaviour extends TestMoveBehaviour {

    /**
     * Initialise the tested object.
     */
    @Override
    @Before
    public void setUp() {
        setMoveBehaviour(new StaticMoveBehaviour());
        setMoveVector(getMoveVector());
    }

}