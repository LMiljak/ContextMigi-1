package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.utility.SummingVectorAggregator;
import com.jme3.math.Vector3f;

/**
 * Test suite for the MultiMoveBehaviour class.
 */
public class TestMultiMoveBehaviour {

	private MultiMoveBehaviour behaviour;

	private MoveBehaviour subBehaviour1;
	private MoveBehaviour subBehaviour2;
	private SummingVectorAggregator aggregator;

	/**
	 * Initialises the test fields.
	 */
	@Before
	public void setUp() {
		subBehaviour1 = Mockito.mock(MoveBehaviour.class);
		subBehaviour2 = Mockito.mock(MoveBehaviour.class);
		aggregator = Mockito.mock(SummingVectorAggregator.class);
		behaviour = Mockito.spy(new MultiMoveBehaviour(aggregator, subBehaviour1, subBehaviour2));
		Mockito.when(aggregator.aggregate(Mockito.any())).thenReturn(Vector3f.ZERO);

	}

	/**
	 * Tests the constructor of MultiMoveBehaviour.
	 */
	@Test
	public void constructorTest() {
		assertTrue(behaviour.getBehaviours().contains(subBehaviour1));
		assertTrue(behaviour.getBehaviours().contains(subBehaviour2));
		assertEquals(2, behaviour.getBehaviours().size());
	}

	@Test
	public void updateMoveVectorTest() {
	    behaviour.updateMoveVector();
	    Mockito.verify(aggregator).aggregate(Mockito.any());
	}
}
