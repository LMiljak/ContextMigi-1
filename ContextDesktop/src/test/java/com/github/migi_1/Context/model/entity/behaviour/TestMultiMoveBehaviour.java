package com.github.migi_1.Context.model.entity.behaviour;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the MultiMoveBehaviour class.
 */
public class TestMultiMoveBehaviour {

	private MultiMoveBehaviour behaviour;
	
	private MoveBehaviour subBehaviour1;
	private MoveBehaviour subBehaviour2;
	private MoveBehaviour subBehaviour3;
	
	/**
	 * Initialises the test fields.
	 */
	@Before
	public void setUp() {
		this.behaviour = new SumMultiMoveBehaviour(subBehaviour1, subBehaviour2, subBehaviour3);
	}
	
	/**
	 * Tests the constructor of MultiMoveBehaviour.
	 */
	@Test
	public void testConstructor() {
		assertTrue(behaviour.behaviours.contains(subBehaviour1));
		assertTrue(behaviour.behaviours.contains(subBehaviour2));
		assertTrue(behaviour.behaviours.contains(subBehaviour3));
		assertEquals(3, behaviour.behaviours.size());
	}
}
