package com.github.migi_1.ContextMessages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the ImmobilisedMessage class.
 */
public class TestImmobilisedMessage {

	private ImmobilisedMessage message;

	/**
	 * Initialises the message field before every test suite.
	 */
	@Before
	public void setUp() {
		message = new ImmobilisedMessage(true, PlatformPosition.FRONTLEFT);
	}

	/**
	 * Tests the getter for the immobilised attribute.
	 */
	@Test
	public void getImmobilisedTest() {
	    assertTrue(message.getImmobilised());
	}

	/**
     * Tests the getter for the immobilised attribute.
     */
    @Test
    public void getPositionTest() {
        assertEquals(PlatformPosition.FRONTLEFT, message.getPosition());
    }


	/**
	 * Tests the empty constructor.
	 */
	@Test
	public void testConstructor() {
	    assertEquals(ImmobilisedMessage.class, new ImmobilisedMessage().getClass());
	    assertEquals(ImmobilisedMessage.class, message.getClass());
	}

}
