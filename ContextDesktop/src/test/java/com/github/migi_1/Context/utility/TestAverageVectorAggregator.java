package com.github.migi_1.Context.utility;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector3f;

/**
 * Test suite for the AverageVectorAggregator class.
 */
public class TestAverageVectorAggregator {

	private IVectorAggregator averager;
	
	/**
	 * Initialises the test fields of this class.
	 */
	@Before
	public void setUp() {
		this.averager = new AverageVectorAggregator();
	}
	
	/**
	 * Asserts that the aggregate method return the zero vector
	 * if the input vector collection is empty.
	 */
	@Test
	public void testAggregateNoVectors() {
		assertEquals(new Vector3f(0, 0, 0), averager.aggregate(new ArrayList<>()));
	}
	
	/**
	 * Tests that the aggregate method runs correctly with only one vector as input.
	 */
	@Test
	public void testAggregateOneVector() {
		assertEquals(new Vector3f(1, 2, 3), averager.aggregate(Arrays.asList(new Vector3f(1, 2, 3))));
	}
	
	/**
	 * Tests the aggregate method using multiple vectors.
	 */
	@Test
	public void testAggregateMultipleVectors() {
		assertEquals(new Vector3f(3, 2, 2), averager.aggregate(Arrays.asList(
					new Vector3f(3, 1, 0),
					new Vector3f(3, 2, 2),
					new Vector3f(3, 3, 4)
				)));
	}
}
