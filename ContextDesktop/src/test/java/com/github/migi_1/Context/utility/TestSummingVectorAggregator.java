package com.github.migi_1.Context.utility;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector3f;

/**
 * Test suite for the SummingVectorAggregator class.
 */
public class TestSummingVectorAggregator {

	private SummingVectorAggregator summer;
	
	/**
	 * Initialises the test fields of this test suite.
	 */
	@Before
	public void setUp() {
		this.summer = new SummingVectorAggregator();
	}
	
	/**
	 * Asserts that the aggregate method returns the zero vector when
	 * called with an empty list.
	 */
	@Test
	public void testAggregateNoVectors() {
		assertEquals(new Vector3f(0, 0, 0), summer.aggregate(new ArrayList<>()));
	}
	
	/**
	 * Tests the aggregate method using one vector as input.
	 */
	@Test
	public void testAggregateOneVector() {
		assertEquals(new Vector3f(1, 2, 3), summer.aggregate(Arrays.asList(new Vector3f(1, 2, 3))));
	}
	
	/**
	 * Tests the aggregate method using multiple vectors as input.
	 */
	@Test
	public void testAggregateMultipleVectors() {
		assertEquals(new Vector3f(-4, 37, -48), summer.aggregate(Arrays.asList(
					new Vector3f(1, 2, 3),
					new Vector3f(-5, -7, 18),
					new Vector3f(0, 42, -69)
				)));
	}
}
