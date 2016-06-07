package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.migi_1.Context.utility.SummingVectorAggregator;

/**
 * Test suite for the MultiMoveBehaviour class.
 */
public class TestMultiMoveBehaviour {

	private MultiMoveBehaviour behaviour;
	
	private MoveBehaviour subBehaviour1;
	private MoveBehaviour subBehaviour2;
	
	/**
	 * Initialises the test fields.
	 */
	@Before
	public void setUp() {
		this.subBehaviour1 = Mockito.mock(MoveBehaviour.class);
		this.subBehaviour2 = Mockito.mock(MoveBehaviour.class);
		this.behaviour = new MultiMoveBehaviour(new SummingVectorAggregator(), subBehaviour1, subBehaviour2);
	}
	
	/**
	 * Tests the constructor of MultiMoveBehaviour.
	 */
	@Test
	public void testConstructor() {
		assertTrue(behaviour.getBehaviours().contains(subBehaviour1));
		assertTrue(behaviour.getBehaviours().contains(subBehaviour2));
		assertEquals(2, behaviour.getBehaviours().size());
	}
}
