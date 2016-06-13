package com.github.migi_1.Context.utility;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector3f;

/**
 * Test suite for the DistanceVectorAggregator class.
 */
public class TestDistanceVectorAggregator {

	private DistanceVectorAggregator aggregator;
	
	/**
	 * Initialises the test fields for the tests.
	 */
	@Before
	public void setUp() {
		this.aggregator = new DistanceVectorAggregator();
	}
	
	/**
	 * Tests the aggregate method using no vectors.
	 */
	@Test
	public void testAggregateNoVectors() {
		assertEquals(Vector3f.ZERO, aggregator.aggregate(Arrays.asList()));
	}
	
	/**
	 * Tests the aggregate method using one vector as input.
	 */
	@Test
	public void testAggregateOneVector() {
		assertEquals(Vector3f.ZERO, aggregator.aggregate(Arrays.asList(new Vector3f(1, 2, 3))));
	}
	
	/**
	 * Tests the aggregate method using multiple vectors as input.
	 */
	@Test
	public void testAggregateMultipleVectors() {
		Vector3f result = aggregator.aggregate(Arrays.asList(
													new Vector3f(1, 2, 3),
													new Vector3f(2, 3, 4),
													new Vector3f(-2, 5, 12)
												));
		
		assertEquals(1.70, result.x, 0.01);
		assertEquals(1.25, result.y, 0.01);
		assertEquals(4.03, result.z, 0.01);
	}
	
}
