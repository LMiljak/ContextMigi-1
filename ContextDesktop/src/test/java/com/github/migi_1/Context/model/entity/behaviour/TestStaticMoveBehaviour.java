package com.github.migi_1.Context.model.entity.behaviour;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test class for the StaticMoveBehaviour class.
 * @author Marcel
 *
 */
public class TestStaticMoveBehaviour extends TestMoveBehaviour {

    private StaticMoveBehaviour behaviour;

    /**
     * Initialise the tested object.
     */
    @Override
    @Before
    public void setUp() {
        behaviour = Mockito.spy(new StaticMoveBehaviour());
        setMoveBehaviour(behaviour);
        setMoveVector(getMoveVector());
    }

    @Test
    public void updateMoveVectorTest() {
        behaviour.updateMoveVector();
        Mockito.verify(behaviour).updateMoveVector();
        Mockito.verifyNoMoreInteractions(behaviour);
    }

}
