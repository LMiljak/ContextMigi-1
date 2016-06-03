package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the PositionMessage class.
 */
public class TestPositionMessage {

	private PositionMessage positionMessage;
	
	/**
	 * Initialises the positionMessage field.
	 */
	@Before
	public void setUp() {
		this.positionMessage = new PositionMessage(PlatformPosition.BACKRIGHT);
	}
	
	/**
	 * Tests the getPosition method.
	 */
	@Test
	public void testGetPosition() {
		assertEquals(PlatformPosition.BACKRIGHT, positionMessage.getPosition());
	}
	
}
