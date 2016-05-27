package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the AccelerometerMessage class.
 */
public class TestAccelerometerMessage {

	private AccelerometerMessage message;

	private final float x_force = 1.0F;
	private final float y_force = 2.0F;
	private final float z_force = 3.0F;

	/**
	 * Initialises the message field before every test suite.
	 */
	@Before
	public void setUp() {
		message = new AccelerometerMessage(x_force, y_force, z_force);
	}

	@Test
	public void testGetX_Force() {
		assertEquals(x_force, message.getX_force(), 0.0F);
	}

	@Test
	public void testGetY_Force() {
		assertEquals(y_force, message.getY_force(), 0.0F);
	}

	@Test
	public void testGetZ_Force() {
		assertEquals(z_force, message.getZ_force(), 0.0F);
	}

	@Test
	public void testConstructor() {
	    AccelerometerMessage acmsg = new AccelerometerMessage();
	    assertEquals(acmsg.getClass(), AccelerometerMessage.class);
	}

}
