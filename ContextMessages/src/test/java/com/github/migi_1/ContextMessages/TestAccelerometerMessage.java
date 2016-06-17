package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jme3.math.Vector3f;

/**
 * Test suite for the AccelerometerMessage class.
 */
public class TestAccelerometerMessage {

	private AccelerometerMessage message;

	private final float xForce = 1.0F;
	private final float yForce = 2.0F;
	private final float zForce = 3.0F;

	/**
	 * Initialises the message field before every test suite.
	 */
	@Before
	public void setUp() {
		message = new AccelerometerMessage(new Vector3f(xForce, yForce, zForce));
	}

	/**
	 * Tests the getXForce method. 
	 */
	@Test
	public void testGetX_Force() {
		assertEquals(xForce, message.getForces().x, 0.0F);
	}

	/**
	 * Tests the getYForce method.
	 */
	@Test
	public void testGetY_Force() {
		assertEquals(yForce, message.getForces().y, 0.0F);
	}

	/**
	 * Tests the getZForce method.
	 */
	@Test
	public void testGetZ_Force() {
		assertEquals(zForce, message.getForces().z, 0.0F);
	}

	/**
	 * Tests the empty constructor (mainly here for the extra code coverage).
	 */
	@Test
	public void testConstructor() {
	    AccelerometerMessage acmsg = new AccelerometerMessage();
	    assertEquals(acmsg.getClass(), AccelerometerMessage.class);
	}

}
